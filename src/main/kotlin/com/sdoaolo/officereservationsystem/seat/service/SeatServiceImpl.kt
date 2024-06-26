package com.sdoaolo.officereservationsystem.seat.service


import com.sdoaolo.officereservationsystem.employee.WorkType
import com.sdoaolo.officereservationsystem.employee.entity.Employee
import com.sdoaolo.officereservationsystem.employee.repository.EmployeeRepository
import com.sdoaolo.officereservationsystem.error.ErrorMessage
import com.sdoaolo.officereservationsystem.error.exception.BusinessException
import com.sdoaolo.officereservationsystem.seat.dto.SeatInformationDto
import com.sdoaolo.officereservationsystem.seat.dto.request.ReservationDto
import com.sdoaolo.officereservationsystem.seat.dto.request.RegisterSeatDto
import com.sdoaolo.officereservationsystem.seat.entity.EmployeeSeat
import com.sdoaolo.officereservationsystem.seat.entity.Seat
import com.sdoaolo.officereservationsystem.seat.repository.EmployeeSeatRepository
import com.sdoaolo.officereservationsystem.seat.repository.SeatRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.transaction.Transactional

@Service
@Transactional
class SeatServiceImpl (
    private val seatRepository: SeatRepository,
    private val employeeRepository: EmployeeRepository,
    private val employeeSeatRepository: EmployeeSeatRepository
): SeatService {

    override fun registerNewSeat(seatDto: RegisterSeatDto): SeatInformationDto {
        val registeredSeat = seatRepository.save(seatDto.toEntity())
        return  SeatInformationDto(
            seatLocation = registeredSeat.seatLocation,
            seatNumber = registeredSeat.seatNumber,
            seatTotalCount = seatRepository.count()
        )
    }

    override fun makeReservation(reservationInfo: ReservationDto): ReservationDto {

        //체크0) 사용가능한 좌석이 남아있는지 확인
        checkRemainingSeats()

        //체크1,2) 예약자, 좌석이 존재하는 번호인지 확인한다.
        val employee = verifiedEmployee(reservationInfo.employeeNumber)
        val seat = verifiedSeat(reservationInfo.seatNumber)

        //체크3
        // 3-1 예약자가 이미 좌석을 예약했는지 확인한다. (이미 예약을 했는가?)
        // 사용 불가능(다른 좌석 이미 예약함) - 이미 예약이 완료된 사용자입니다.
        val employeeReservedData = employeeSeatRepository.findByEmployeeNumberToday(reservationInfo.employeeNumber)
            ?.let { throw BusinessException(ErrorMessage.ALREADY_RESERVED_EMPLOYEE) }

        // 3-2 좌석이 사용할수 있는지 확인한다. (누가 사용하고 있는가?)
        // 사용 불가능(누군가 사용중) - 이미 예약된 좌석입니다. 다른 좌석을 선택하세요 (응답 반환)
        val seatReservedData = employeeSeatRepository.findBySeatNumberToday(reservationInfo.seatNumber)
            ?.let{ throw BusinessException(ErrorMessage.ALREADY_RESERVED_SEAT) }

        //체크4) 이미 employee-seat에 데이터가 있는가? (예약자가 예약했던 좌석인가?) --isValid는 true/false 상관없다.
        //4-1 존재하는 경우 - 예약했다 취소한 좌석은 다시 예약할 수 없습니다. (응답 반환)
        val cancelData = employeeSeatRepository.findByEmployeeAndSeatAndReserveDate(employee, seat, LocalDate.now())
            ?.let{ throw BusinessException(ErrorMessage.CANNOT_RE_RESERVE) }

        val newReservation = EmployeeSeat(
            employee = employee,
            seat = seat,
            isValid = true
        )

        employee.currentWorkType = WorkType.오피스출근

        return ReservationDto.fromEntity(
            employeeSeatRepository.save(newReservation))
    }

    override fun cancelReservation(reservationInfo: ReservationDto): ReservationDto {

        //1) 예약자와 좌석의 데이터 존재 확인
        val employee = verifiedEmployee(reservationInfo.employeeNumber)
        verifiedSeat(reservationInfo.seatNumber)

        //2) 예약자-좌석 : 실제로 예약되어있는지, isValid = true
        val cancelData = employeeSeatRepository.findByEmployeeSeatNumberToday(reservationInfo.employeeNumber, reservationInfo.seatNumber)
            ?: throw BusinessException(ErrorMessage.RESERVATION_NOT_FOUND)

        //여기까지 왔으면 취소해도 되는 예약!
        cancelData.isValid = false
        employee.currentWorkType = WorkType.미출근

        return reservationInfo //단순 취소자와 취소된 좌석번호에 대한 정보만 반환해도 되므로 그대로 반환
    }

    private fun verifiedEmployee(employeeNumber: Short): Employee {
        val employee = employeeRepository.findByEmployeeNumber(employeeNumber)
            ?: throw BusinessException(ErrorMessage.EMPLOYEE_NOT_FOUND)
        return employee
    }

    private fun verifiedSeat(seatNumber: Short): Seat {
        val seat = seatRepository.findBySeatNumber(seatNumber)
            ?: throw BusinessException(ErrorMessage.SEAT_NOT_FOUND)
        return seat
    }

    private fun checkRemainingSeats() {
        //좌석 자체가 없는경우
        val activeSeats = employeeSeatRepository.countByIsValidTrue()
        val totalSeats = seatRepository.count()

        if (totalSeats - activeSeats < 1) {
            val employeesNotPresent = employeeRepository.findByCurrentWorkType(WorkType.미출근)
            employeesNotPresent.forEach { it.currentWorkType = WorkType.재택 }

            throw BusinessException(ErrorMessage.NO_REMAINING_SEATS)
        }
    }
}
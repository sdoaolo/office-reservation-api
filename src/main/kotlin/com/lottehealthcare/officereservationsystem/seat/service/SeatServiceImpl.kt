package com.lottehealthcare.officereservationsystem.seat.service


import com.lottehealthcare.officereservationsystem.employee.WorkType
import com.lottehealthcare.officereservationsystem.employee.repository.EmployeeRepository
import com.lottehealthcare.officereservationsystem.error.ErrorMessage
import com.lottehealthcare.officereservationsystem.error.exception.BusinessException
import com.lottehealthcare.officereservationsystem.seat.dto.SeatInformationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.ReservationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.RegisterSeatDto
import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat
import com.lottehealthcare.officereservationsystem.seat.repository.EmployeeSeatRepository
import com.lottehealthcare.officereservationsystem.seat.repository.SeatRepository
import org.springframework.stereotype.Service
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

        //체크1) 예약자 번호가 존재하는 번호인지 확인한다.
        val employee = employeeRepository.findByEmployeeNumber(reservationInfo.employeeNumber)
            ?: throw BusinessException(ErrorMessage.EMPLOYEE_NOT_FOUND)

        //체크2) 좌석이 존재하는지
        //2-1) 존재하지 않을 경우 - 존재하지 않는 좌석정보입니다. (응답 반환)
        val seat = seatRepository.findBySeatNumber(reservationInfo.seatNumber)
            ?: throw BusinessException(ErrorMessage.SEAT_NOT_FOUND)

        //체크3
        // 3-1 예약자가 이미 좌석을 예약했는지 확인한다. (이미 예약을 했는가?)
        // 사용 불가능(다른 좌석 이미 예약함) - 이미 예약이 완료된 사용자입니다.
        val employeeReservedData = employeeSeatRepository.findByEmployeeNumber(reservationInfo.employeeNumber)
            ?.let { throw BusinessException(ErrorMessage.ALREADY_RESERVED_EMPLOYEE) }

        // 3-2 좌석이 사용할수 있는지 확인한다. (누가 사용하고 있는가?)
        // 사용 불가능(누군가 사용중) - 이미 예약된 좌석입니다. 다른 좌석을 선택하세요 (응답 반환)
        val seatReservedData = employeeSeatRepository.findBySeatNumber(reservationInfo.seatNumber)
            ?.let{ throw BusinessException(ErrorMessage.ALREADY_RESERVED_SEAT) }


        //체크4) 이미 employee-seat에 데이터가 있는가? (예약자가 예약했던 좌석인가?)
        //4-1 존재하는 경우 - 예약했다 취소한 좌석은 다시 예약할 수 없습니다. (응답 반환)
        val cancelData = employeeSeatRepository.findByEmployeeAndSeat(employee, seat)
            ?.let{ throw BusinessException(ErrorMessage.CANNOT_RE_RESERVE) }


        // 여기까지 왔으면 사용가능한 경우
        // employee-seat 테이블에 데이터 추가
        val newReservation = EmployeeSeat(
            employee = employee,
            seat = seat,
            isValid = true
        )

        //employee의 currentWorkType을 "오피스출근"으로 바꿔준다.
        employee.currentWorkType = WorkType.오피스출근

        return ReservationDto.fromEntity(
            employeeSeatRepository.save(newReservation))
    }
}
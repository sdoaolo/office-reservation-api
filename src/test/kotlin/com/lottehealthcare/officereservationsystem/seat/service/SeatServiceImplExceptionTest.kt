package com.lottehealthcare.officereservationsystem.seat.service

import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import com.lottehealthcare.officereservationsystem.employee.repository.EmployeeRepository
import com.lottehealthcare.officereservationsystem.error.ErrorMessage
import com.lottehealthcare.officereservationsystem.error.exception.BusinessException
import com.lottehealthcare.officereservationsystem.seat.dto.request.ReservationDto
import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat
import com.lottehealthcare.officereservationsystem.seat.entity.Seat
import com.lottehealthcare.officereservationsystem.seat.repository.EmployeeSeatRepository
import com.lottehealthcare.officereservationsystem.seat.repository.SeatRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.*

class SeatServiceImplExceptionTest {

    private lateinit var seatService: SeatService
    private lateinit var seatRepository: SeatRepository
    private lateinit var employeeRepository: EmployeeRepository
    private lateinit var employeeSeatRepository: EmployeeSeatRepository


    private val tmpNumber = 1.toShort()
    private val tmpReservationDto = ReservationDto(tmpNumber,tmpNumber)

    @BeforeEach
    fun setUp() {
        seatRepository = mockk()
        employeeRepository = mockk()
        employeeSeatRepository = mockk()

        seatService = SeatServiceImpl(seatRepository,employeeRepository, employeeSeatRepository)
    }

    @Test
    @DisplayName("[예약] 모든 좌석이 이용중, 남는 좌석이 없음")
    fun noRemainingSeats() {

        // Given
        val activeSeats = 2L
        val totalSeats = 2L
        every { employeeSeatRepository.countByIsValidTrue() } returns activeSeats
        every { seatRepository.count() } returns totalSeats

        // When & Then
        assertThrows<BusinessException> {
            seatService.makeReservation(tmpReservationDto)
        }.apply {
            Assertions.assertEquals(ErrorMessage.NO_REMAINING_SEATS, this.errorMessage)
        }
    }

    @Test
    @DisplayName("[예약/취소] 직원 데이터가 존재하지 않음")
    fun notFoundEmployee() {

        // Given
        every { employeeRepository.findByEmployeeNumber(any()) } returns null

        // When & Then
        assertThrows<BusinessException> {
            seatService.cancelReservation(tmpReservationDto)
        }.apply {
            Assertions.assertEquals(ErrorMessage.EMPLOYEE_NOT_FOUND, this.errorMessage)
        }
    }

    @Test
    @DisplayName("[예약/취소] 좌석 데이터가 존재하지 않음")
    fun notFoundSeat() {

        // Given
        every { employeeRepository.findByEmployeeNumber(any()) } returns Employee("")
        every { seatRepository.findBySeatNumber(any()) } returns null

        // When & Then
        assertThrows<BusinessException> {
            seatService.cancelReservation(tmpReservationDto)
        }.apply {
            Assertions.assertEquals(ErrorMessage.SEAT_NOT_FOUND, this.errorMessage)
        }
    }

    @Test
    @DisplayName("[예약] 사용자가 이미 다른 좌석을 예약")
    fun alreadyReservedEmployee() {

        // Given
        val employeeNumber = 1.toShort()
        val makeReservationInfo = ReservationDto(employeeNumber, tmpNumber)

        val employee = Employee("")
        employee.employeeNumber = employeeNumber

        val employeeReservedData = EmployeeSeat(
            employee= employee,
            seat= Seat(""),
            isValid = true
        )

        every { employeeSeatRepository.countByIsValidTrue() } returns 1L
        every { seatRepository.count() } returns 2L
        every { employeeRepository.findByEmployeeNumber(any()) } returns Employee("")
        every { seatRepository.findBySeatNumber(any()) } returns Seat("")


        every { employeeSeatRepository.findByEmployeeNumber(any()) } returns employeeReservedData

        // When & Then
        assertThrows<BusinessException> {
            seatService.makeReservation(makeReservationInfo)
        }.apply {
            Assertions.assertEquals(ErrorMessage.ALREADY_RESERVED_EMPLOYEE, this.errorMessage)
        }
    }

    @Test
    @DisplayName("[예약] 좌석이 이미 예약되었음")
    fun alreadyReservedSeat() {

        // Given
        val seatNumber = 1.toShort()
            val makeReservationInfo = ReservationDto(tmpNumber, seatNumber)

        val seat = Seat("")
        seat.seatNumber = seatNumber

        val seatReservedData = EmployeeSeat(
            employee= Employee(""),
            seat= seat,
            isValid = true
        )

        every { employeeSeatRepository.countByIsValidTrue() } returns 1L
        every { seatRepository.count() } returns 2L
        every { employeeRepository.findByEmployeeNumber(any()) } returns Employee("")
        every { seatRepository.findBySeatNumber(any()) } returns Seat("")


        every { employeeSeatRepository.findByEmployeeNumber(any()) } returns null
        every { employeeSeatRepository.findBySeatNumber(any()) } returns seatReservedData

        // When & Then
        assertThrows<BusinessException> {
            seatService.makeReservation(makeReservationInfo)
        }.apply {
            Assertions.assertEquals(ErrorMessage.ALREADY_RESERVED_SEAT, this.errorMessage)
        }
    }

    @Test
    @DisplayName("[예약] 예약했다가 취소한 좌석")
    fun canNotReReserve() {

        // Given
        val cancelData = EmployeeSeat(
            employee= Employee(""),
            seat=  Seat(""),
            isValid = false
        )

        every { employeeSeatRepository.countByIsValidTrue() } returns 1L
        every { seatRepository.count() } returns 2L
        every { employeeRepository.findByEmployeeNumber(any()) } returns Employee("")
        every { seatRepository.findBySeatNumber(any()) } returns Seat("")
        every { employeeSeatRepository.findByEmployeeNumber(any()) } returns null
        every { employeeSeatRepository.findBySeatNumber(any()) } returns null

        every { employeeSeatRepository.findByEmployeeAndSeat(any(), any()) } returns cancelData

        // When & Then
        assertThrows<BusinessException> {
            seatService.makeReservation(tmpReservationDto)
        }.apply {
            Assertions.assertEquals(ErrorMessage.CANNOT_RE_RESERVE, this.errorMessage)
        }
    }

    @Test
    @DisplayName("[취소] 예약 데이터가 존재하지 않음")
    fun notFoundReservation() {

        // Given
        val employeeNumber = 1.toShort()
        val seatNumber = 1.toShort()

        every { employeeRepository.findByEmployeeNumber(any()) } returns Employee("")
        every { seatRepository.findBySeatNumber(any()) } returns Seat("")

        every { employeeSeatRepository.findByEmployeeSeatNumber(employeeNumber,seatNumber) } returns null

        // When & Then
        assertThrows<BusinessException> {
            seatService.cancelReservation(tmpReservationDto)
        }.apply {
            Assertions.assertEquals(ErrorMessage.RESERVATION_NOT_FOUND, this.errorMessage)
        }
    }
}
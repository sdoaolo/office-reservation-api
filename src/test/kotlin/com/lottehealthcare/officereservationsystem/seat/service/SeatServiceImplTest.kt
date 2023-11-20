package com.lottehealthcare.officereservationsystem.seat.service

import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import com.lottehealthcare.officereservationsystem.employee.repository.EmployeeRepository
import com.lottehealthcare.officereservationsystem.seat.dto.SeatInformationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.RegisterSeatDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.ReservationDto
import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat
import com.lottehealthcare.officereservationsystem.seat.entity.Seat
import com.lottehealthcare.officereservationsystem.seat.repository.EmployeeSeatRepository
import com.lottehealthcare.officereservationsystem.seat.repository.SeatRepository

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.*

class SeatServiceImplTest {

    private lateinit var seatService: SeatService
    private lateinit var seatRepository: SeatRepository
    private lateinit var employeeRepository: EmployeeRepository
    private lateinit var employeeSeatRepository: EmployeeSeatRepository

    @BeforeEach
    fun setUp() {
        seatRepository = mockk()
        employeeRepository = mockk()
        employeeSeatRepository = mockk()

        seatService = SeatServiceImpl(seatRepository,employeeRepository, employeeSeatRepository)
    }

    @Test
    @DisplayName("[등록] 새로운 좌석 등록")
    fun registerNewSeatTest() {

        // Given
        val seatLocation = "롯데월드타워 27F Room A"
        val seatDto = RegisterSeatDto(seatLocation)

        val registeredSeat = Seat(seatLocation).apply { seatNumber = 1.toShort() }
        val expectedSeatNumber = 1.toShort()
        val expectedSeatCount = 1L // 기존에 0개의 좌석이 있다고 가정, 현재 좌석이 추가되었으므로 total 1
        val expectedSeatDto = SeatInformationDto(seatLocation, expectedSeatNumber, expectedSeatCount)

        every { seatRepository.save(any()) } returns registeredSeat
        every { seatRepository.count() } returns expectedSeatCount

        // When
        val registeredSeatInfo = seatService.registerNewSeat(seatDto)

        // Then
        Assertions.assertEquals(expectedSeatDto.seatLocation, registeredSeatInfo.seatLocation)
        Assertions.assertEquals(expectedSeatDto.seatNumber, registeredSeatInfo.seatNumber)
        Assertions.assertEquals(expectedSeatDto.seatTotalCount, registeredSeatInfo.seatTotalCount)
    }

    @Test
    @DisplayName("[예약] 성공")
    fun makeReservationSuccessTest() {

        // Given
        val employeeNumber = 1.toShort()
        val seatNumber = 1.toShort()

        val makeReservationInfo = ReservationDto(employeeNumber, seatNumber)

        val employee = Employee("지은")
        val seat = Seat("롯데월드타워 27F Room A")
        employee.employeeNumber = employeeNumber
        seat.seatNumber = seatNumber

        val newReservation = EmployeeSeat(
            employee= employee,
            seat=seat,
            isValid = true
        )

        //check 0
        val allDataNumber = 2L
        val usableSeatNumber = 1L
        every { employeeSeatRepository.countByIsValidTrue() } returns usableSeatNumber
        every { seatRepository.count() } returns allDataNumber

        //check 1, 2
        every { employeeRepository.findByEmployeeNumber(any()) } returns employee
        every { seatRepository.findBySeatNumber(any()) } returns seat

        //check 3-1, 3-2, 4
        every { employeeSeatRepository.findByEmployeeNumberToday(any()) } returns null
        every { employeeSeatRepository.findBySeatNumberToday(any()) } returns null
        every { employeeSeatRepository.findByEmployeeAndSeat(any(), any()) } returns null

        //save reservation
        every { employeeSeatRepository.save(any()) } returns newReservation


        // When
        val reservationResponseDto = seatService.makeReservation(makeReservationInfo)

        // Then
        Assertions.assertEquals(reservationResponseDto.employeeNumber, makeReservationInfo.employeeNumber)
        Assertions.assertEquals(reservationResponseDto.seatNumber, makeReservationInfo.seatNumber)
    }

    @Test
    @DisplayName("[취소] 성공")
    fun cancelReservationSuccessTest() {

        // Given
        val employeeNumber = 1.toShort()
        val seatNumber = 1.toShort()
        val cancelReservationInfo = ReservationDto(employeeNumber, seatNumber)


        val employee = Employee("지은")
        val seat = Seat("롯데월드타워 27F Room A")
        employee.employeeNumber = employeeNumber
        seat.seatNumber = seatNumber

        //check 1
        every { employeeRepository.findByEmployeeNumber(any()) } returns employee
        every { seatRepository.findBySeatNumber(any()) } returns seat


        val expectCancelData = EmployeeSeat(
            employee=employee,
            seat=seat,
            isValid = true
        )

        //check 2
        every { employeeSeatRepository.findByEmployeeSeatNumberToday(any(),any()) } returns expectCancelData

        // When
        val cancelReservation = seatService.cancelReservation(cancelReservationInfo)


        // Then
        Assertions.assertEquals(cancelReservation.seatNumber, seatNumber)
        Assertions.assertEquals(cancelReservation.employeeNumber, employeeNumber)
    }
}
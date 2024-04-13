package com.sdoaolo.officereservationsystem.employee.service

import com.sdoaolo.officereservationsystem.employee.WorkType
import com.sdoaolo.officereservationsystem.employee.dto.response.SimpleImformationEmployeeDto
import com.sdoaolo.officereservationsystem.employee.entity.Employee
import com.sdoaolo.officereservationsystem.employee.repository.EmployeeRepository
import com.sdoaolo.officereservationsystem.seat.entity.EmployeeSeat
import com.sdoaolo.officereservationsystem.seat.entity.Seat
import com.sdoaolo.officereservationsystem.seat.repository.EmployeeSeatRepository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

class EmployeeServiceImplTest {
    private lateinit var employeeService: EmployeeService
    private lateinit var employeeRepository: EmployeeRepository
    private lateinit var employeeSeatRepository: EmployeeSeatRepository

    private val size = 20

    @BeforeEach
    fun setUp() {

        employeeRepository = mockk()
        employeeSeatRepository = mockk()

        employeeService = EmployeeServiceImpl(employeeRepository, employeeSeatRepository)
    }

    @Test
    @DisplayName("[등록] 새로운 직원 등록")
    fun registerNewEmployeeTest() {

        // Given
        val name = "강지은"
        val employee = Employee(
            employeeName = name
        ).apply { employeeNumber = 1.toShort() }

        val expectedEmployeeDto = SimpleImformationEmployeeDto.fromEntity(employee)

        every { employeeRepository.save(any()) } returns employee

        // When
        val registeredEmployeeInfo = employeeService.registerNewEmployee(employee)

        // Then
        Assertions.assertEquals(expectedEmployeeDto.name, registeredEmployeeInfo.name)
        Assertions.assertEquals(expectedEmployeeDto.employeeNumber, registeredEmployeeInfo.employeeNumber)
    }

    @Test
    @DisplayName("[조회] 현재 직원의 근무 상태 20명 조회")
    fun getAllEmployeeWorkStatus() {

        // Given
        val page = 1
        val pageRequest = PageRequest.of(page-1, size)

        val employee01SeatNumber = 42.toShort()
        val employees = mutableListOf(
            Employee("강지은").apply {
                employeeNumber = 1.toShort()
                currentWorkType = WorkType.오피스출근
            },
            Employee("홍길동").apply {
                employeeNumber = 2.toShort()
                currentWorkType = WorkType.재택
            }
        )
        for (i in 3..20) {
            employees.add( Employee("Employee$i").apply { employeeNumber = i.toShort() } )
        }


        val employeePage = PageImpl(employees, pageRequest, employees.size.toLong())
        val employeeSeat = EmployeeSeat(
            employee = Employee(""),
            seat = Seat("").apply { seatNumber = employee01SeatNumber },
            isValid = true
        )

        every { employeeRepository.findAll(pageRequest) } returns employeePage
        every { employeeSeatRepository.findByEmployeeNumberToday(1) } returns employeeSeat
        every { employeeSeatRepository.findByEmployeeNumberToday(2) } returns null

        // When
        val result = employeeService.getAllEmployeeWorkStatus(page)

        // Then
        Assertions.assertEquals(result.size, employees.size)

        result.content.forEach { employeeWorkStatusDto ->

            val employee = employees.find { it.employeeNumber == employeeWorkStatusDto.employeeNumber }

            assertNotNull(employee)
            Assertions.assertEquals(employeeWorkStatusDto.name, employee!!.employeeName)
            Assertions.assertEquals(employeeWorkStatusDto.currentWorkType, employee!!.currentWorkType)

            if (employee.currentWorkType == WorkType.오피스출근) {

                Assertions.assertEquals(employeeWorkStatusDto.seatNumber, employeeSeat.seat.seatNumber)
            } else {
                assertNull(employeeWorkStatusDto.seatNumber)
            }
        }

        verify(exactly = 1) { employeeRepository.findAll(pageRequest) }
        verify(atLeast = 1) { employeeSeatRepository.findByEmployeeNumberToday(any()) }
    }
}
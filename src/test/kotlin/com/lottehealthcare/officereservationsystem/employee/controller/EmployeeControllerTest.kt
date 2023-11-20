package com.lottehealthcare.officereservationsystem.employee.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lottehealthcare.officereservationsystem.common.ResponseStatus
import com.lottehealthcare.officereservationsystem.employee.WorkType
import com.lottehealthcare.officereservationsystem.employee.dto.request.RegisterNewEmployeeDto
import com.lottehealthcare.officereservationsystem.employee.dto.response.CurrentWorkStatusDto
import com.lottehealthcare.officereservationsystem.employee.dto.response.SimpleImformationEmployeeDto
import com.lottehealthcare.officereservationsystem.employee.service.EmployeeService

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class EmployeeControllerTest {

    private lateinit var mockMvc: MockMvc
    private val employeeService = mockk<EmployeeService>()
    private val objectMapper = ObjectMapper()

    private val defaultUrl = "/api/v1/employees"
    @BeforeEach
    fun setup() {
        val employeeController = EmployeeController(employeeService)
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build()
    }

    @Test
    @DisplayName("직원 등록")
    fun registerNewSeatTest() {

        // Given
        var name = "강지은"
        val employeeNumber = 1.toShort()
        val newEmployeeDto = RegisterNewEmployeeDto(name)

        val registeredEmployeeDto = SimpleImformationEmployeeDto(
            name = name,
            employeeNumber = employeeNumber
        )

        every { employeeService.registerNewEmployee(any()) } returns registeredEmployeeDto

        // When & Then
        mockMvc.perform(
            MockMvcRequestBuilders.post(defaultUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEmployeeDto))
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ResponseStatus.SUCCESS.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseStatus.SUCCESS.code))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("직원을 등록했습니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.employeeNumber").value(employeeNumber.toInt()))
    }


    @Test
    @DisplayName("현재 직원 근무 상태 조회")
    fun getAllEmployeeWorkStatusTest() {

        // Given
        val url = "/work-status"
        var page = 1

        val officeWorkEmployee = CurrentWorkStatusDto(
            name= "강지은",
            employeeNumber = 1,
            currentWorkType = WorkType.오피스출근,
            seatNumber = 31
        )

        val remoteWorkEmployee = CurrentWorkStatusDto(
            name= "홍길동",
            employeeNumber = 2,
            currentWorkType = WorkType.재택
        )

        val workStatusList = listOf(
            officeWorkEmployee,
            remoteWorkEmployee,
            CurrentWorkStatusDto(
                name= "캐즐",
                employeeNumber = 3,
                currentWorkType = WorkType.재택
            )
        )
        val pageSize = 20
        val pageable = PageRequest.of(page, pageSize)
        val workStatusPage = PageImpl(workStatusList, pageable, workStatusList.size.toLong())


        every { employeeService.getAllEmployeeWorkStatus(any()) } returns workStatusPage

        // When & Then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$defaultUrl$url?page=$page")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ResponseStatus.SUCCESS.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseStatus.SUCCESS.code))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("현재 직원들의 근무 상태입니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[*]").isNotEmpty)

            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].currentWorkType").value(officeWorkEmployee.currentWorkType.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].seatNumber").value(officeWorkEmployee.seatNumber?.toInt()))

            .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].currentWorkType").value(remoteWorkEmployee.currentWorkType.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].seatNumber").doesNotExist())
    }
}
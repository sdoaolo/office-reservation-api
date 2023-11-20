package com.lottehealthcare.officereservationsystem.seat.controller

import io.mockk.every
import io.mockk.mockk
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.http.MediaType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.fasterxml.jackson.databind.ObjectMapper
import com.lottehealthcare.officereservationsystem.common.ResponseStatus
import com.lottehealthcare.officereservationsystem.seat.controller.SeatController
import com.lottehealthcare.officereservationsystem.seat.dto.SeatInformationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.RegisterSeatDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.ReservationDto
import com.lottehealthcare.officereservationsystem.seat.service.SeatService
import org.junit.jupiter.api.DisplayName
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

class SeatControllerTest {

    private lateinit var mockMvc: MockMvc
    private val seatService = mockk<SeatService>()
    private val objectMapper = ObjectMapper()

    private val defaultUrl = "/api/v1/seats"
    @BeforeEach
    fun setup() {
        val seatController = SeatController(seatService)
        mockMvc = MockMvcBuilders.standaloneSetup(seatController).build()
    }

    @Test
    @DisplayName("좌석 등록")
    fun registerNewSeatTest() {

        // Given
        var location = "롯데월드타워 27F Room C"
        val registerSeatDto = RegisterSeatDto(location)

        val seatInformationDto = SeatInformationDto(
            seatLocation = location,
            seatNumber = 1.toShort(),
            seatTotalCount = 2L
        )

        every { seatService.registerNewSeat(any()) } returns seatInformationDto

        // When & Then
        mockMvc.perform(
            post(defaultUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerSeatDto))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(ResponseStatus.SUCCESS.code))
            .andExpect(jsonPath("$.message").value("새로운 좌석을 등록했습니다."))
            .andExpect(jsonPath("$.isSuccess").value(true))
            .andExpect(jsonPath("$.data").isNotEmpty)
    }


    @Test
    @DisplayName("좌석 예약")
    fun makeReservationTest() {

        // Given
        val url = "/reservations"
        var employeeNumber = 1.toShort()
        var seatNumber = 1.toShort()
        val reservationDto = ReservationDto(employeeNumber=employeeNumber, seatNumber=seatNumber)


        every { seatService.makeReservation(any()) } returns reservationDto

        // When & Then
        mockMvc.perform(
            post(defaultUrl+ url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservationDto))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(ResponseStatus.SUCCESS.code))
            .andExpect(jsonPath("$.message").value("좌석 예약에 성공했습니다."))
            .andExpect(jsonPath("$.isSuccess").value(true))
            .andExpect(jsonPath("$.data").isNotEmpty)
            .andExpect(jsonPath("$.data.employeeNumber").value(employeeNumber.toInt()))
            .andExpect(jsonPath("$.data.seatNumber").value(seatNumber.toInt()))
    }

    @Test
    @DisplayName("좌석 취소")
    fun cancelReservationTest() {

        // Given
        val url = "/reservations"
        var employeeNumber = 1.toShort()
        var seatNumber = 1.toShort()
        val reservationDto = ReservationDto(employeeNumber=employeeNumber, seatNumber=seatNumber)

        every { seatService.cancelReservation(any()) } returns reservationDto

        // When & Then
        mockMvc.perform(
            delete(defaultUrl+ url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservationDto))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(ResponseStatus.SUCCESS.code))
            .andExpect(jsonPath("$.message").value("좌석 예약이 취소되었습니다."))
            .andExpect(jsonPath("$.isSuccess").value(true))
            .andExpect(jsonPath("$.data").isNotEmpty)
            .andExpect(jsonPath("$.data.employeeNumber").value(employeeNumber.toInt()))
            .andExpect(jsonPath("$.data.seatNumber").value(seatNumber.toInt()))
    }
}
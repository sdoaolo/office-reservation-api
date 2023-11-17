package com.lottehealthcare.officereservationsystem.seat.controller

import com.lottehealthcare.officereservationsystem.common.ApplicationResponseDto
import com.lottehealthcare.officereservationsystem.common.ResponseStatus
import com.lottehealthcare.officereservationsystem.seat.dto.SeatInformationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.ReservationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.RegisterSeatDto
import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat
import com.lottehealthcare.officereservationsystem.seat.service.SeatService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/seats")
class SeatController (
    private val seatService: SeatService
) {
    @PostMapping
    fun registerNewSeat(@RequestBody registerSeatDto: RegisterSeatDto): ApplicationResponseDto<SeatInformationDto> {

        val seatInformation = seatService.registerNewSeat(registerSeatDto)

        return ApplicationResponseDto(
            ResponseStatus.SUCCESS,
            "새로운 좌석을 등록했습니다.",
            ResponseStatus.SUCCESS.code,
            true,
            seatInformation
        )
    }
    @PostMapping("/reservations")
    fun makeReservation(@RequestBody reservationDto: ReservationDto): ApplicationResponseDto<ReservationDto> {

        val reservation = seatService.makeReservation(reservationDto)

        return ApplicationResponseDto(
            ResponseStatus.SUCCESS,
            "좌석 예약에 성공했습니다.",
            ResponseStatus.SUCCESS.code,
            true,
            reservation
        )
    }
}
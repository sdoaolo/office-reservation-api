package com.lottehealthcare.officereservationsystem.seat.controller

import com.lottehealthcare.officereservationsystem.common.ApplicationResponseDto
import com.lottehealthcare.officereservationsystem.common.ResponseStatus
import com.lottehealthcare.officereservationsystem.seat.dto.SeatInformationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.ReservationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.RegisterSeatDto
import com.lottehealthcare.officereservationsystem.seat.service.SeatService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(tags = ["Seat API"])
@RestController
@RequestMapping("/api/v1/seats")
class SeatController (
    private val seatService: SeatService
) {
    @PostMapping
    @ApiOperation(value = "새 좌석 등록", notes = "시스템에 새로운 좌석을 등록합니다.")
    fun registerNewSeat(@RequestBody @Valid registerSeatDto: RegisterSeatDto): ApplicationResponseDto<SeatInformationDto> {

        return ApplicationResponseDto(
            ResponseStatus.SUCCESS,
            "새로운 좌석을 등록했습니다.",
            ResponseStatus.SUCCESS.code,
            true,
            seatService.registerNewSeat(registerSeatDto)
        )
    }

    @PostMapping("/reservations")
    @Operation(summary="좌석 예약", description= "제공된 예약 정보를 사용하여 좌석을 예약합니다.")
    fun makeReservation(@RequestBody @Valid reservationDto: ReservationDto): ApplicationResponseDto<ReservationDto> {

        return ApplicationResponseDto(
            ResponseStatus.SUCCESS,
            "좌석 예약에 성공했습니다.",
            ResponseStatus.SUCCESS.code,
            true,
            seatService.makeReservation(reservationDto)
        )
    }

    @DeleteMapping("/reservations")
    @ApiOperation(value = "좌석 예약 취소", notes = "제공된 예약 정보를 사용하여 해당 예약을 취소합니다.")
    fun cancelReservation(@RequestBody @Valid reservationDto: ReservationDto) : ApplicationResponseDto<ReservationDto> {

        return ApplicationResponseDto(
            ResponseStatus.SUCCESS,
            "좌석 예약이 취소되었습니다.",
            ResponseStatus.SUCCESS.code,
            true,
            seatService.cancelReservation(reservationDto)
        )
    }
}
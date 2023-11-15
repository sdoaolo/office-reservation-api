package com.lottehealthcare.officereservationsystem.seat.controller

import com.lottehealthcare.officereservationsystem.seat.service.SeatService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/seats")
class SeatController (
    private val seatService : SeatService
) {

    @PostMapping
    fun makeReservation() : String {
        return "좌석 예약"
    }

    @DeleteMapping
    fun cancelReservation() : String {
        return "좌석 취소"
    }
}
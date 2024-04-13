package com.sdoaolo.officereservationsystem.seat.repository

import com.sdoaolo.officereservationsystem.seat.entity.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository: JpaRepository<Seat, Long> {
    fun findBySeatNumber(seatNumber: Short): Seat?
}
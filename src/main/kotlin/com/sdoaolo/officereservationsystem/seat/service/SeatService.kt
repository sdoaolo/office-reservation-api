package com.sdoaolo.officereservationsystem.seat.service

import com.sdoaolo.officereservationsystem.seat.dto.SeatInformationDto
import com.sdoaolo.officereservationsystem.seat.dto.request.ReservationDto
import com.sdoaolo.officereservationsystem.seat.dto.request.RegisterSeatDto

interface SeatService {

    fun registerNewSeat(seat: RegisterSeatDto): SeatInformationDto
    fun makeReservation(reservationInfo: ReservationDto): ReservationDto
    fun cancelReservation(reservationInfo: ReservationDto): ReservationDto
}
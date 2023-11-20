package com.lottehealthcare.officereservationsystem.seat.service

import com.lottehealthcare.officereservationsystem.seat.dto.SeatInformationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.ReservationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.RegisterSeatDto

interface SeatService {

    fun registerNewSeat(seat: RegisterSeatDto): SeatInformationDto
    fun makeReservation(reservationInfo: ReservationDto): ReservationDto
    fun cancelReservation(reservationInfo: ReservationDto): ReservationDto
}
package com.sdoaolo.officereservationsystem.seat.dto

data class SeatInformationDto (
    var seatLocation: String,
    var seatNumber: Short?,
    var seatTotalCount: Long
)
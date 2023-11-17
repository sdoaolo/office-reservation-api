package com.lottehealthcare.officereservationsystem.seat.dto.request

import com.lottehealthcare.officereservationsystem.common.mapping.ToEntityConvertible
import com.lottehealthcare.officereservationsystem.seat.entity.Seat

data class RegisterSeatDto (
    var seatLocation: String
): ToEntityConvertible<Seat> {
    override fun toEntity(): Seat {
        return Seat(
            seatLocation = seatLocation
        )
    }
}
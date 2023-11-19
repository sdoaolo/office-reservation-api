package com.lottehealthcare.officereservationsystem.seat.dto.request

import com.lottehealthcare.officereservationsystem.common.mapping.ToEntityConvertible
import com.lottehealthcare.officereservationsystem.seat.entity.Seat
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterSeatDto (

    @field:NotBlank(message = "Location must not be blank")
    @field:Size(max = 50, message = "The maximum number of characters is 50")
    var seatLocation: String
): ToEntityConvertible<Seat> {
    override fun toEntity(): Seat {
        return Seat(
            seatLocation = seatLocation
        )
    }
}
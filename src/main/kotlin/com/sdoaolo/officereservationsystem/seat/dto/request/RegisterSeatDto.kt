package com.sdoaolo.officereservationsystem.seat.dto.request

import com.sdoaolo.officereservationsystem.common.mapping.ToEntityConvertible
import com.sdoaolo.officereservationsystem.seat.entity.Seat
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterSeatDto (

    @ApiModelProperty(name = "seatLocation",
        value = "좌석의 상세 위치 : 빈 문자열 불가능, 최대 50자까지 입력 가능 (예: '롯데월드타워 27F Room A')",
        required = true)
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
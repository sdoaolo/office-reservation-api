package com.sdoaolo.officereservationsystem.error

import com.sdoaolo.officereservationsystem.common.ResponseStatus

enum class ErrorMessage(
    val responseStatus: ResponseStatus,
    val message: String
) {
    //403 Forbidden (권한 부족)
    CANNOT_RE_RESERVE( ResponseStatus.BAD_REQUEST, "Previously reserved seats cannot be re-reserved"),

    //404 Not Found
    EMPLOYEE_NOT_FOUND(ResponseStatus.NOT_FOUND,"Employee Not Found"),
    SEAT_NOT_FOUND(ResponseStatus.NOT_FOUND,"Seat Not Found"),
    RESERVATION_NOT_FOUND(ResponseStatus.NOT_FOUND,"Reservation Not Found"),

    //409 Conflict (서버와 현재 상태 충돌),
    ALREADY_RESERVED_EMPLOYEE(ResponseStatus.CONFLICT,"This user has already completed a reservation"),
    ALREADY_RESERVED_SEAT(ResponseStatus.CONFLICT,"This seat is already reserved. Please choose a different seat"),

    //422 Unprocessable Entity : 요청은 명확하나, 처리할 수 없음
    NO_REMAINING_SEATS(ResponseStatus.UNPROCESSABLE_ENTITY, "There are no remaining seats. ")
}
package com.lottehealthcare.officereservationsystem.error

enum class ErrorMessage(
    val code: Int,
    val message: String
) {
    //403 Forbidden (권한 부족)
    CANNOT_RE_RESERVE(403, "Previously reserved seats cannot be re-reserved"),

    //404 Not Found
    EMPLOYEE_NOT_FOUND(404,"Employee Not Found"),
    SEAT_NOT_FOUND(404,"Seat Not Found"),

    //409 Conflict (서버와 현재 상태 충돌),
    ALREADY_RESERVED_EMPLOYEE(409,"This user has already completed a reservation"),
    ALREADY_RESERVED_SEAT(409, "This seat is already reserved. Please choose a different seat.")
}
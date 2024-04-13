package com.sdoaolo.officereservationsystem.common

enum class ResponseStatus(
    val code: Long?
) {
    SUCCESS(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    CONFLICT(409),
    UNPROCESSABLE_ENTITY(422),
    INTERNAL_SERVER_ERROR(500)
}
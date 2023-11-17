package com.lottehealthcare.officereservationsystem.common

data class ApplicationResponseDto<T>(
    val status: ResponseStatus,
    val message: String,
    val code : Long?,
    val isSuccess: Boolean,
    val data: T
)

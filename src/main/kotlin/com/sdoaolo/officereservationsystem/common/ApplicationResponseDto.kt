package com.sdoaolo.officereservationsystem.common

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApplicationResponseDto<T>(
    val status: ResponseStatus,
    val message: String,
    val code : Long?,
    val isSuccess: Boolean,
    val data: T?
)
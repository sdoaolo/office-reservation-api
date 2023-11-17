package com.lottehealthcare.officereservationsystem.common.mapping

interface ToEntityConvertible<T> {
    fun toEntity(): T
}
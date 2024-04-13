package com.sdoaolo.officereservationsystem.common.mapping

interface ToEntityConvertible<T> {
    fun toEntity(): T
}
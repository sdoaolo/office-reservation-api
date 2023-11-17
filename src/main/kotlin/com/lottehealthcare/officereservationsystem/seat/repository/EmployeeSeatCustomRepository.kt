package com.lottehealthcare.officereservationsystem.seat.repository

import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat

interface EmployeeSeatCustomRepository {
    fun findUsingInfo(seatNumber: Short?): EmployeeSeat?
}
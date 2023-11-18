package com.lottehealthcare.officereservationsystem.seat.repository

import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat

interface EmployeeSeatCustomRepository {
    fun findBySeatNumber(seatNumber: Short?): EmployeeSeat?
    fun findByEmployeeNumber(employeeNumber: Short?): EmployeeSeat?
    fun findByEmployeeSeatNumber(employeeNumber: Short?, seatNumber: Short?): EmployeeSeat?
}
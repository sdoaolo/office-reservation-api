package com.lottehealthcare.officereservationsystem.seat.repository

import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat

interface EmployeeSeatCustomRepository {
    fun findBySeatNumberToday(seatNumber: Short?): EmployeeSeat?
    fun findByEmployeeNumberToday(employeeNumber: Short?): EmployeeSeat?
    fun findByEmployeeSeatNumberToday(employeeNumber: Short?, seatNumber: Short?): EmployeeSeat?
}
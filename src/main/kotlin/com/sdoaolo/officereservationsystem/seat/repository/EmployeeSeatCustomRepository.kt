package com.sdoaolo.officereservationsystem.seat.repository

import com.sdoaolo.officereservationsystem.seat.entity.EmployeeSeat

interface EmployeeSeatCustomRepository {
    fun findBySeatNumberToday(seatNumber: Short?): EmployeeSeat?
    fun findByEmployeeNumberToday(employeeNumber: Short?): EmployeeSeat?
    fun findByEmployeeSeatNumberToday(employeeNumber: Short?, seatNumber: Short?): EmployeeSeat?
}
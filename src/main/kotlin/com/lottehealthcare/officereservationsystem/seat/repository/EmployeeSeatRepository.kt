package com.lottehealthcare.officereservationsystem.seat.repository

import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat
import com.lottehealthcare.officereservationsystem.seat.entity.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeSeatRepository: JpaRepository<EmployeeSeat, Long>, EmployeeSeatCustomRepository {
    fun findByEmployeeAndSeat(employee: Employee, seat: Seat): EmployeeSeat?
    fun countByIsValidTrue(): Long
}
package com.lottehealthcare.officereservationsystem.seat.repository

import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat
import com.lottehealthcare.officereservationsystem.seat.entity.Seat
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface EmployeeSeatRepository: JpaRepository<EmployeeSeat, Long>, EmployeeSeatCustomRepository {

    fun findByEmployeeAndSeatAndReserveDate(employee: Employee, seat: Seat, reserveDate: LocalDate): EmployeeSeat?
    fun countByIsValidTrue(): Long
}
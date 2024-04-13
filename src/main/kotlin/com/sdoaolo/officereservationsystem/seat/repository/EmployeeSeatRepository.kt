package com.sdoaolo.officereservationsystem.seat.repository

import com.sdoaolo.officereservationsystem.employee.entity.Employee
import com.sdoaolo.officereservationsystem.seat.entity.EmployeeSeat
import com.sdoaolo.officereservationsystem.seat.entity.Seat
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface EmployeeSeatRepository: JpaRepository<EmployeeSeat, Long>, EmployeeSeatCustomRepository {

    fun findByEmployeeAndSeatAndReserveDate(employee: Employee, seat: Seat, reserveDate: LocalDate): EmployeeSeat?
    fun countByIsValidTrue(): Long
}
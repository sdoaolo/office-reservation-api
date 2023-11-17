package com.lottehealthcare.officereservationsystem.employee.repository

import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository: JpaRepository<Employee, Long> {
    fun findByEmployeeNumber(employeeNumber: Short): Employee
}
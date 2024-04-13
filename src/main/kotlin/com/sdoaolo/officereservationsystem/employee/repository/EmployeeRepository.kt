package com.sdoaolo.officereservationsystem.employee.repository

import com.sdoaolo.officereservationsystem.employee.WorkType
import com.sdoaolo.officereservationsystem.employee.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository: JpaRepository<Employee, Long> {
    fun findByEmployeeNumber(employeeNumber: Short): Employee?
    fun findByCurrentWorkType(workType: WorkType): List<Employee>
}
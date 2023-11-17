package com.lottehealthcare.officereservationsystem.employee.repository

import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import org.springframework.data.repository.CrudRepository

interface EmployeeRepository: CrudRepository<Employee, Long> {
}
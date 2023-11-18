package com.lottehealthcare.officereservationsystem.employee.service

import com.lottehealthcare.officereservationsystem.employee.dto.response.CurrentWorkStatusDto
import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import org.springframework.data.domain.Page

interface EmployeeService {
    fun registerNewEmployee(employee: Employee): Employee
    fun getAllEmployeeWorkStatus(page: Int): Page<CurrentWorkStatusDto>
}
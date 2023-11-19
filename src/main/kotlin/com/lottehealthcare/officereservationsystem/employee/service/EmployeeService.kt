package com.lottehealthcare.officereservationsystem.employee.service

import com.lottehealthcare.officereservationsystem.employee.dto.response.CurrentWorkStatusDto
import com.lottehealthcare.officereservationsystem.employee.dto.response.SimpleImformationEmployeeDto
import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import org.springframework.data.domain.Page

interface EmployeeService {
    fun registerNewEmployee(employee: Employee): SimpleImformationEmployeeDto
    fun getAllEmployeeWorkStatus(page: Int): Page<CurrentWorkStatusDto>
}
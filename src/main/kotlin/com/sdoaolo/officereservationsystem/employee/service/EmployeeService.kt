package com.sdoaolo.officereservationsystem.employee.service

import com.sdoaolo.officereservationsystem.employee.dto.response.CurrentWorkStatusDto
import com.sdoaolo.officereservationsystem.employee.dto.response.SimpleImformationEmployeeDto
import com.sdoaolo.officereservationsystem.employee.entity.Employee
import org.springframework.data.domain.Page

interface EmployeeService {
    fun registerNewEmployee(employee: Employee): SimpleImformationEmployeeDto
    fun getAllEmployeeWorkStatus(page: Int): Page<CurrentWorkStatusDto>
}
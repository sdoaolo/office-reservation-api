package com.lottehealthcare.officereservationsystem.employee.service

import com.lottehealthcare.officereservationsystem.employee.dto.response.CurrentWorkStatusDto
import com.lottehealthcare.officereservationsystem.employee.entity.Employee

interface EmployeeService {
    fun registerNewEmployee(employee: Employee): Employee
    fun getAllEmployeeWorkStatus():  List<CurrentWorkStatusDto>
}
package com.lottehealthcare.officereservationsystem.employee.service

import com.lottehealthcare.officereservationsystem.employee.dto.response.CurrentWorkStatusDto
import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import com.lottehealthcare.officereservationsystem.employee.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl (
    private val employeeRepository: EmployeeRepository
): EmployeeService {
    override fun registerNewEmployee(employee: Employee): Employee {
        val createdEmployee = employeeRepository.save(employee)
        return createdEmployee
    }
}
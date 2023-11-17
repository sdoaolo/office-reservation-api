package com.lottehealthcare.officereservationsystem.employee.dto.request

import com.lottehealthcare.officereservationsystem.common.mapping.ToEntityConvertible
import com.lottehealthcare.officereservationsystem.employee.entity.Employee

data class RegisterNewEmployeeDto(
    var name: String,
): ToEntityConvertible<Employee> {
    override fun toEntity(): Employee {
        return Employee(
            employeeName = name
        )
    }
}
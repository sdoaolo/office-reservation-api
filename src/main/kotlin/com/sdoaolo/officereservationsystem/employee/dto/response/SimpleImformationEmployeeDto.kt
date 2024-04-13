package com.sdoaolo.officereservationsystem.employee.dto.response

import com.sdoaolo.officereservationsystem.employee.entity.Employee

data class SimpleImformationEmployeeDto (
    var name: String,
    var employeeNumber: Short
) {
    companion object {
        fun fromEntity(employee: Employee): SimpleImformationEmployeeDto {
            return SimpleImformationEmployeeDto(
                name = employee.employeeName,
                employeeNumber = employee.employeeNumber!!
            )
        }
    }
}
package com.lottehealthcare.officereservationsystem.employee.dto.response

import com.lottehealthcare.officereservationsystem.employee.WorkType
import com.lottehealthcare.officereservationsystem.employee.entity.Employee
data class CurrentWorkStatusDto(
    var name: String,
    var employeeNumber: Short,
    var currentWorkType : WorkType
) {
    companion object {
        fun fromEntity(employee: Employee): CurrentWorkStatusDto {
            return CurrentWorkStatusDto(
                name = employee.employeeName,
                employeeNumber = employee.employeeNumber!!,
                currentWorkType = employee.currentWorkType!!
            )
        }
    }
}
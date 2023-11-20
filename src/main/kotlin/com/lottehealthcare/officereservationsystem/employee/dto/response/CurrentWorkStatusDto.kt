package com.lottehealthcare.officereservationsystem.employee.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.lottehealthcare.officereservationsystem.employee.WorkType
import com.lottehealthcare.officereservationsystem.employee.entity.Employee

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CurrentWorkStatusDto(
    var name: String,
    var employeeNumber: Short,
    var currentWorkType : WorkType,
    var seatNumber: Short?= null
) {
    companion object {
        fun fromEntity(employee: Employee, seatNumber: Short?): CurrentWorkStatusDto {
            return CurrentWorkStatusDto(
                name = employee.employeeName,
                employeeNumber = employee.employeeNumber!!,
                currentWorkType = employee.currentWorkType!!,
                seatNumber = if (employee.currentWorkType == WorkType.오피스출근) seatNumber else null
            )
        }
    }
}
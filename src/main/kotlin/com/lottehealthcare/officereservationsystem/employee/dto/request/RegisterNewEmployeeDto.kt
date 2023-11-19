package com.lottehealthcare.officereservationsystem.employee.dto.request

import com.lottehealthcare.officereservationsystem.common.mapping.ToEntityConvertible
import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterNewEmployeeDto(

    @field:NotBlank
    @field:Size(max = 20, message = "The maximum number of characters is 20")
    var name: String,
): ToEntityConvertible<Employee> {
    override fun toEntity(): Employee {
        return Employee(
            employeeName = name
        )
    }
}
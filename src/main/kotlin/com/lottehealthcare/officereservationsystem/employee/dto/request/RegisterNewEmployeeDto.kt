package com.lottehealthcare.officereservationsystem.employee.dto.request

import com.lottehealthcare.officereservationsystem.common.mapping.ToEntityConvertible
import com.lottehealthcare.officereservationsystem.employee.entity.Employee
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterNewEmployeeDto(

    @ApiModelProperty(name = "name", value = "직원의 이름. 빈 문자열 불가능, 최대 20자까지 입력 가능", required = true)
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
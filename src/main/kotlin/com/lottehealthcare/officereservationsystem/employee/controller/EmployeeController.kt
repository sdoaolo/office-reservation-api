


package com.lottehealthcare.officereservationsystem.seat.controller

import com.lottehealthcare.officereservationsystem.common.ApplicationResponseDto
import com.lottehealthcare.officereservationsystem.common.ResponseStatus
import com.lottehealthcare.officereservationsystem.employee.dto.request.RegisterNewEmployeeDto
import com.lottehealthcare.officereservationsystem.employee.dto.response.SimpleImformationEmployeeDto
import com.lottehealthcare.officereservationsystem.employee.service.EmployeeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/employees")
class EmployeeController(
    private val employeeService: EmployeeService
){
    @PostMapping
    fun registerNewEmployee(@RequestBody registerEmployee: RegisterNewEmployeeDto): ApplicationResponseDto<SimpleImformationEmployeeDto> {

        val employee = employeeService.registerNewEmployee(registerEmployee.toEntity())
        return ApplicationResponseDto(
            ResponseStatus.SUCCESS,
            "직원을 등록했습니다.",
            ResponseStatus.SUCCESS.code,
            true,
            SimpleImformationEmployeeDto.fromEntity(employee)
        )
    }
    @GetMapping
    @RequestMapping("/work-status")
    fun getAllEmployeeWorkStatus() : String {
        return "모든 직원의 근무상태를 반환합니다."
    }
}
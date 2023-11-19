package com.lottehealthcare.officereservationsystem.seat.controller

import com.lottehealthcare.officereservationsystem.common.ApplicationResponseDto
import com.lottehealthcare.officereservationsystem.common.ResponseStatus
import com.lottehealthcare.officereservationsystem.employee.dto.request.RegisterNewEmployeeDto
import com.lottehealthcare.officereservationsystem.employee.dto.response.CurrentWorkStatusDto
import com.lottehealthcare.officereservationsystem.employee.dto.response.SimpleImformationEmployeeDto
import com.lottehealthcare.officereservationsystem.employee.service.EmployeeService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/employees")
class EmployeeController(
    private val employeeService: EmployeeService
){
    @PostMapping
    fun registerNewEmployee(@RequestBody @Valid registerEmployee: RegisterNewEmployeeDto): ApplicationResponseDto<SimpleImformationEmployeeDto> {

        return ApplicationResponseDto(
            ResponseStatus.SUCCESS,
            "직원을 등록했습니다.",
            ResponseStatus.SUCCESS.code,
            true,
            employeeService.registerNewEmployee(registerEmployee.toEntity())
        )
    }
    @GetMapping
    @RequestMapping("/work-status")
    fun getAllEmployeeWorkStatus(@RequestParam("page") page: Int) : ApplicationResponseDto<List<CurrentWorkStatusDto>> {

        return ApplicationResponseDto(
            ResponseStatus.SUCCESS,
            "현재 직원들의 근무 상태입니다.",
            ResponseStatus.SUCCESS.code,
            true,
            employeeService.getAllEmployeeWorkStatus(page).content
        )
    }
}
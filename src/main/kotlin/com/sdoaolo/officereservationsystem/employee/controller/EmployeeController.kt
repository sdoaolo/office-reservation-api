package com.sdoaolo.officereservationsystem.employee.controller

import com.sdoaolo.officereservationsystem.common.ApplicationResponseDto
import com.sdoaolo.officereservationsystem.common.ResponseStatus
import com.sdoaolo.officereservationsystem.employee.dto.request.RegisterNewEmployeeDto
import com.sdoaolo.officereservationsystem.employee.dto.response.CurrentWorkStatusDto
import com.sdoaolo.officereservationsystem.employee.dto.response.SimpleImformationEmployeeDto
import com.sdoaolo.officereservationsystem.employee.service.EmployeeService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(tags = ["Employee API"])
@RestController
@RequestMapping("/api/v1/employees")
class EmployeeController(
    private val employeeService: EmployeeService
){

    @PostMapping
    @ApiOperation(value = "새 직원 등록", notes = "새로운 직원을 시스템에 등록합니다.")
    fun registerNewEmployee(@RequestBody @Valid registerEmployee: RegisterNewEmployeeDto): ApplicationResponseDto<SimpleImformationEmployeeDto> {

        return ApplicationResponseDto(
            ResponseStatus.SUCCESS,
            "직원을 등록했습니다.",
            ResponseStatus.SUCCESS.code,
            true,
            employeeService.registerNewEmployee(registerEmployee.toEntity())
        )
    }
    @GetMapping ("/work-status")
    @ApiOperation(value = "현재 직원 근무 상태 조회", notes = "직원들의 근무 상태를 조회합니다. 페이지 번호를 매개변수로 제공해야하며, 페이지는 1번부터 조회 가능합니다.")
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
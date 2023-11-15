


package com.lottehealthcare.officereservationsystem.seat.controller

import com.lottehealthcare.officereservationsystem.seat.service.SeatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/employees")

class EmployeeController {

    @GetMapping
    @RequestMapping("/work-status")
    fun getAllEmployeeWorkStatus() : String {
        return "모든 직원의 근무상태를 반환합니다."
    }
}
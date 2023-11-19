package com.lottehealthcare.officereservationsystem.seat.dto.request

import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat
import org.jetbrains.annotations.NotNull
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class ReservationDto(

    @field:NotNull
    @field:Min(1) @field:Max(150)
    var employeeNumber: Short,

    @field:NotNull
    @field:Min(1) @field:Max(100)
    var seatNumber : Short
){
    companion object {
        fun fromEntity(employeeSeat: EmployeeSeat): ReservationDto {
            return ReservationDto(
                employeeNumber = employeeSeat.employee.employeeNumber!!,
                seatNumber = employeeSeat.seat.seatNumber!!
            )
        }
    }
}
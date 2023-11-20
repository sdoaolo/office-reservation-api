package com.lottehealthcare.officereservationsystem.seat.dto.request

import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat
import io.swagger.annotations.ApiModelProperty
import org.jetbrains.annotations.NotNull
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class ReservationDto(

    @ApiModelProperty(name = "employeeNumber", value = "예약자의 직원 번호 (1~150)", dataType = "Number", required = true)
    @field:NotNull
    @field:Min(1) @field:Max(150)
    var employeeNumber: Short,

    @ApiModelProperty(name = "seatNumber", value = "예약하려는 좌석의 번호 (1~100)", dataType = "Number", required = true)
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
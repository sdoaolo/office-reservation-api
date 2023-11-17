package com.lottehealthcare.officereservationsystem.seat.dto.request

import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat

data class ReservationDto(
    var employeeNumber: Short,
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
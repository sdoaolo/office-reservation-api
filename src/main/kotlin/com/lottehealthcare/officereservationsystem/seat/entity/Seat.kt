package com.lottehealthcare.officereservationsystem.seat.entity

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "seat")
class Seat (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeId")
    val seatId: Long? = null,

    @Column(nullable = false, length = 50)
    var seatLocation: String,

    @Column(nullable = false)
    var seatNumber: Short,

    @Column
    @CreatedDate
    val createdDate : LocalDate = LocalDate.now()
)
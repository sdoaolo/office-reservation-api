package com.sdoaolo.officereservationsystem.seat.entity

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "seat")
class Seat (
    @Column(nullable = false, length = 50)
    var seatLocation: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatId")
    val seatId: Long? = null

    @Column(nullable = false)
    var seatNumber: Short? = 0

    @Column
    @CreatedDate
    val createdDate : LocalDate = LocalDate.now()

    @PostPersist
    fun setSeatNumber() {
        seatNumber = seatId?.toShort()!!
    }
}
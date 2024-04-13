package com.sdoaolo.officereservationsystem.seat.entity

import com.sdoaolo.officereservationsystem.employee.entity.Employee
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "employee_seat", uniqueConstraints = [UniqueConstraint(columnNames = ["employee_id", "seat_id", "reserve_date"])])
class EmployeeSeat (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    val employee: Employee,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    val seat: Seat,

    @Column(name = "is_valid", nullable = false)
    var isValid: Boolean,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null

    @Column(name = "reserve_date", nullable = false)
    val reserveDate: LocalDate = LocalDate.now()
}
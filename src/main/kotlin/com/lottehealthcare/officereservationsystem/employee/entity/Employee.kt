package com.lottehealthcare.officereservationsystem.employee.entity

import com.lottehealthcare.officereservationsystem.employee.WorkType
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "employee")
class Employee (
    @Column(nullable = false, length = 20)
    var employeeName: String
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeId")
    val employeeId: Long? = null

    @Column
    @Enumerated(EnumType.STRING)
    var currentWorkType : WorkType?= WorkType.재택

    @Column(nullable = false)
    var employeeNumber: Short? = 0

    @Column
    @CreatedDate
    val createdDate : LocalDate = LocalDate.now()

    @PostPersist
    fun setEmployeeNumber() {
        employeeNumber = employeeId?.toShort()!!
    }
}
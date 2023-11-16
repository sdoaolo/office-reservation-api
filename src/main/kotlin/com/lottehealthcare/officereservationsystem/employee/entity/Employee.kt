package com.lottehealthcare.officereservationsystem.employee.entity

import com.lottehealthcare.officereservationsystem.employee.WorkType
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "employee")
class Employee (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeId")
    val employeeId: Long? = null, //id pk값이므로, 변경불가인 val 사용

    @Column(nullable = false, length = 20)
    var name: String, //이름은 변경될 수 있으므로 var

    @Column(nullable = false)
    var employeeNumber: Short,

    @Column
    @Enumerated(EnumType.STRING)
    var currentWorkType : WorkType,

    @Column
    @CreatedDate
    val createdDate : LocalDate = LocalDate.now() // 생성 날짜는 생성 시 설정되고 변경되지 않음

)
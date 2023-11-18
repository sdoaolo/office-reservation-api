package com.lottehealthcare.officereservationsystem.seat.repository

import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat
import com.lottehealthcare.officereservationsystem.seat.entity.QEmployeeSeat
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class EmployeeSeatCustomRepositoryImpl (
        private val jpaQueryFactory: JPAQueryFactory
): EmployeeSeatCustomRepository {

    private val qEmployeeSeat = QEmployeeSeat.employeeSeat
    override fun findBySeatNumber(seatNumber: Short?): EmployeeSeat? {
            return jpaQueryFactory
                .selectFrom(qEmployeeSeat)
                .where(qEmployeeSeat.seat.seatNumber.eq(seatNumber),
                    qEmployeeSeat.isValid.isTrue)
                .fetchOne()
    }
    override fun findByEmployeeNumber(employeeNumber: Short?): EmployeeSeat? {
        return jpaQueryFactory
            .selectFrom(qEmployeeSeat)
            .where(qEmployeeSeat.employee.employeeNumber.eq(employeeNumber),
                qEmployeeSeat.isValid.isTrue)
            .fetchOne()
    }

    override fun findByEmployeeSeatNumber(employeeNumber: Short?, seatNumber: Short?): EmployeeSeat? {
        return  jpaQueryFactory
            .selectFrom(qEmployeeSeat)
            .where(qEmployeeSeat.seat.seatNumber.eq(seatNumber),
                qEmployeeSeat.employee.employeeNumber.eq(employeeNumber),
                qEmployeeSeat.isValid.isTrue)
            .fetchOne()
    }
}
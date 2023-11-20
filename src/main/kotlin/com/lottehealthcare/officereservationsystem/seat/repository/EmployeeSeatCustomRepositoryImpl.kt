package com.lottehealthcare.officereservationsystem.seat.repository

import com.lottehealthcare.officereservationsystem.seat.entity.EmployeeSeat
import com.lottehealthcare.officereservationsystem.seat.entity.QEmployeeSeat
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDate
import javax.persistence.LockModeType

@Repository
class EmployeeSeatCustomRepositoryImpl (
        private val jpaQueryFactory: JPAQueryFactory
): EmployeeSeatCustomRepository {

    private val qEmployeeSeat = QEmployeeSeat.employeeSeat
    override fun findBySeatNumberToday(seatNumber: Short?): EmployeeSeat? {
            return jpaQueryFactory
                .selectFrom(qEmployeeSeat)
                .where(qEmployeeSeat.seat.seatNumber.eq(seatNumber),
                    qEmployeeSeat.isValid.isTrue,
                    qEmployeeSeat.reserveDate.eq(LocalDate.now()))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE) // 비관적 Lock 적용
                .fetchOne()
    }
    override fun findByEmployeeNumberToday(employeeNumber: Short?): EmployeeSeat? {
        return jpaQueryFactory
            .selectFrom(qEmployeeSeat)
            .where(qEmployeeSeat.employee.employeeNumber.eq(employeeNumber),
                qEmployeeSeat.isValid.isTrue,
                qEmployeeSeat.reserveDate.eq(LocalDate.now()))
            .fetchOne()
    }

    override fun findByEmployeeSeatNumberToday(employeeNumber: Short?, seatNumber: Short?): EmployeeSeat? {
        return  jpaQueryFactory
            .selectFrom(qEmployeeSeat)
            .where(qEmployeeSeat.seat.seatNumber.eq(seatNumber),
                qEmployeeSeat.employee.employeeNumber.eq(employeeNumber),
                qEmployeeSeat.isValid.isTrue,
                qEmployeeSeat.reserveDate.eq(LocalDate.now()))
            .fetchOne()
    }
}
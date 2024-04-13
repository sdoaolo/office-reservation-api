package com.sdoaolo.officereservationsystem.seat.repository

import com.sdoaolo.officereservationsystem.employee.entity.Employee
import com.sdoaolo.officereservationsystem.seat.entity.EmployeeSeat
import com.sdoaolo.officereservationsystem.seat.entity.Seat
import com.mysema.commons.lang.Assert.assertThat
import com.querydsl.jpa.impl.JPAQueryFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest


@DataJpaTest
@SpringBootTest
class EmployeeSeatCustomRepositoryImplTest {

    private val jpaQueryFactory = mockk<JPAQueryFactory>()

    private val repository = EmployeeSeatCustomRepositoryImpl(jpaQueryFactory)


    @Test
    @DisplayName("")
    fun findBySeatNumberToday() {
      /*  // Given
        val seatNumber: Short = 1
        val expectedEmployeeSeat = EmployeeSeat(
            employee = Employee(""),
            seat = Seat(""),
            true
            ) // 적절한 값으로 채워주세요.

        every {
            jpaQueryFactory.selectFrom(seatNumber)
                .where(any(), any(), any())
                .setLockMode(any())
                .fetchOne()
        } returns expectedEmployeeSeat

        // When
        val result = repository.findBySeatNumberToday(seatNumber)

        // Then
        assertThat(result).isEqualTo(expectedEmployeeSeat)
        verify {
            jpaQueryFactory.selectFrom(any()).where(any(), any(), any()).setLockMode(any()).fetchOne()
        }*/
    }
}
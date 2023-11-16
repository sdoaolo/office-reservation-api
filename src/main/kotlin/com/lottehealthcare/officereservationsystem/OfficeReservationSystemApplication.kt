package com.lottehealthcare.officereservationsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class OfficeReservationSystemApplication

fun main(args: Array<String>) {
	runApplication<OfficeReservationSystemApplication>(*args)
}

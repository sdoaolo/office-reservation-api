package com.sdoaolo.officereservationsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
class OfficeReservationSystemApplication

fun main(args: Array<String>) {
	runApplication<com.sdoaolo.officereservationsystem.OfficeReservationSystemApplication>(*args)
}

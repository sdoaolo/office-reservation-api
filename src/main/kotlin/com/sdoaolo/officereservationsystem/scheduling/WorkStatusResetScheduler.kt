package com.sdoaolo.officereservationsystem.scheduling

import com.sdoaolo.officereservationsystem.employee.WorkType
import com.sdoaolo.officereservationsystem.employee.repository.EmployeeRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class WorkStatusResetScheduler(
    private val employeeRepository: EmployeeRepository
) {
    @Scheduled(cron = "0 0 0 * * ?")
    fun resetEmployeeWorkStatus() {
        val allEmployees = employeeRepository.findAll()
        for (employee in allEmployees) {
            employee.currentWorkType = WorkType.미출근
        }
        employeeRepository.saveAll(allEmployees)
    }
}
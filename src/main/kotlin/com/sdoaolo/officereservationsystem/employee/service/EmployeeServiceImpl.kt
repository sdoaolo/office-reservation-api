package com.sdoaolo.officereservationsystem.employee.service

import com.sdoaolo.officereservationsystem.employee.WorkType
import com.sdoaolo.officereservationsystem.employee.dto.response.CurrentWorkStatusDto
import com.sdoaolo.officereservationsystem.employee.dto.response.SimpleImformationEmployeeDto
import com.sdoaolo.officereservationsystem.employee.entity.Employee
import com.sdoaolo.officereservationsystem.employee.repository.EmployeeRepository
import com.sdoaolo.officereservationsystem.seat.repository.EmployeeSeatRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class EmployeeServiceImpl (
    private val employeeRepository: EmployeeRepository,
    private val employeeSeatRepository: EmployeeSeatRepository,

    @Value("\${paging.defaultSize}")
    private var size: Int = 20 // application.yml 단에서 변경 가능 (Default=20으로 설정)

): EmployeeService {
    override fun registerNewEmployee(employee: Employee): SimpleImformationEmployeeDto {
        return  SimpleImformationEmployeeDto.fromEntity(employeeRepository.save(employee))
    }
    override fun getAllEmployeeWorkStatus(page: Int): Page<CurrentWorkStatusDto> {

        val pageRequest = PageRequest.of(page-1, size)
        val employeePage = employeeRepository.findAll(pageRequest)

        return employeePage.map { employee ->
            var seatNumber: Short? = null
            if(employee.currentWorkType == WorkType.오피스출근){
                seatNumber = employeeSeatRepository.findByEmployeeNumberToday(employee.employeeNumber)?.seat?.seatNumber
            }
            CurrentWorkStatusDto.fromEntity(employee, seatNumber)
        }
    }
}
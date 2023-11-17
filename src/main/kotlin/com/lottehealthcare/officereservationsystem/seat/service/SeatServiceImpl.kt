package com.lottehealthcare.officereservationsystem.seat.service

import com.lottehealthcare.officereservationsystem.seat.dto.SeatInformationDto
import com.lottehealthcare.officereservationsystem.seat.dto.request.RegisterSeatDto
import com.lottehealthcare.officereservationsystem.seat.repository.SeatRepository
import org.springframework.stereotype.Service

@Service
class SeatServiceImpl (
    private val seatRepository: SeatRepository
): SeatService {

    override fun registerNewSeat(seatDto: RegisterSeatDto): SeatInformationDto {
        val registeredSeat = seatRepository.save(seatDto.toEntity())
        return  SeatInformationDto(
            seatLocation = registeredSeat.seatLocation,
            seatNumber = registeredSeat.seatNumber,
            seatTotalCount = seatRepository.count()
        )
    }
}
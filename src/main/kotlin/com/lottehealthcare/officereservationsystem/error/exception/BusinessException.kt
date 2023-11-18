package com.lottehealthcare.officereservationsystem.error.exception

import com.lottehealthcare.officereservationsystem.error.ErrorMessage

class BusinessException(
    val errorMessage: ErrorMessage
): RuntimeException(errorMessage.message)
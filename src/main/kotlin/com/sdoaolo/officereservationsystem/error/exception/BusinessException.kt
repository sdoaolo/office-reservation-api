package com.sdoaolo.officereservationsystem.error.exception

import com.sdoaolo.officereservationsystem.error.ErrorMessage

class BusinessException(
    val errorMessage: ErrorMessage
): RuntimeException(errorMessage.message)
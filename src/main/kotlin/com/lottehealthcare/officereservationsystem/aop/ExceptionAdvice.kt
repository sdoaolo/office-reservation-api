package com.lottehealthcare.officereservationsystem.aop

import com.lottehealthcare.officereservationsystem.common.ApplicationResponseDto
import com.lottehealthcare.officereservationsystem.common.ResponseStatus
import com.lottehealthcare.officereservationsystem.error.exception.BusinessException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ApplicationResponseDto<Any?> {

        val errors =
            e.bindingResult.fieldErrors.map { fieldError -> "${fieldError.field}: ${fieldError.defaultMessage}" }

        val errorMessage: String = if (errors.isNotEmpty()) {
            errors.joinToString(", ")
        } else {
            "잘못된 요청입니다"
        }

        return ApplicationResponseDto(
            status = ResponseStatus.BAD_REQUEST,
            message = errorMessage,
            code = ResponseStatus.BAD_REQUEST.code,
            isSuccess = false,
            data = null
        )
    }
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleUrlNotFoundException(
        e: NoHandlerFoundException,
        request: HttpServletRequest
    ): ApplicationResponseDto<Any?> =
        ApplicationResponseDto(
            status = ResponseStatus.NOT_FOUND,
            message = "Request failed",
            code = ResponseStatus.NOT_FOUND.code,
            isSuccess = false,
            data = null)

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(
        e: BusinessException,
        request: HttpServletRequest
    ): ApplicationResponseDto<Any?> {

        return ApplicationResponseDto(
            status = e.errorMessage.responseStatus,
            message = e.errorMessage.message,
            code = e.errorMessage.responseStatus.code,
            isSuccess = false,
            data = null
        )
    }
}
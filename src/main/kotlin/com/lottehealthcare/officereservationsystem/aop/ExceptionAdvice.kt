package com.lottehealthcare.officereservationsystem.aop

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.lottehealthcare.officereservationsystem.common.ApplicationResponseDto
import com.lottehealthcare.officereservationsystem.common.ResponseStatus
import com.lottehealthcare.officereservationsystem.error.exception.BusinessException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        e: HttpMessageNotReadableException,
        request: WebRequest
    ): ApplicationResponseDto<Any?> {
        val errorMessage = when (val cause = e.cause) {
            is MismatchedInputException -> "${cause.path.joinToString() { it.fieldName }} is null"
            else -> "유효하지 않은 요청입니다"
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
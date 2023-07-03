package com.fasolutions.finance.adapter.`in`.error

import com.fasolutions.finance.application.domain.NotFoundException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class RestExceptionHandler {

    private val log = KotlinLogging.logger { }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException) =
        ErrorsExternal("error.mapping", ex.message).also {
            log.error(ex) { ex.message }
        }

    @ExceptionHandler(MissingRequestHeaderException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMissingRequestHeaderException(ex: MissingRequestHeaderException) =
        ErrorsExternal("error.mapping", ex.message).also {
            log.error(ex) { ex.message }
        }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(ex: NotFoundException) =
        ErrorsExternal("error.mapping", ex.message).also {
            log.error(ex) { ex.message }
        }

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleThrowable(ex: Throwable) =
        ErrorsExternal("error.unexpected", ex.message)
            .also {
                log.error(ex) { ex.message }
            }
}

package com.fasolutions.finance.application.domain

class NotFoundException(val code: String, message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(code: String, message: String) : this(code, message, null)
}

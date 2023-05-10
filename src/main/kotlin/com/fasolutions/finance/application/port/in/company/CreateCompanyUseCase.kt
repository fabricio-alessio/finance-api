package com.fasolutions.finance.application.port.`in`.company

interface CreateCompanyUseCase {
    fun create(command: CreateCompanyCommand): String
}

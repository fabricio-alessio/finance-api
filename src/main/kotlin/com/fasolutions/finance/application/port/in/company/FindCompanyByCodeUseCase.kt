package com.fasolutions.finance.application.port.`in`.company

import com.fasolutions.finance.application.domain.Company

interface FindCompanyByCodeUseCase {

    fun findByCode(code: String): Company
}
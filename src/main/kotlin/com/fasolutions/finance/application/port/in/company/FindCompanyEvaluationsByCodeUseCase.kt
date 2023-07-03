package com.fasolutions.finance.application.port.`in`.company

import com.fasolutions.finance.application.domain.CompanyEvaluations

interface FindCompanyEvaluationsByCodeUseCase {
    fun findEvaluationsByUserIdCode(userId: Int, code: String): CompanyEvaluations
}

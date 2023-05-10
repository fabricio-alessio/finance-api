package com.fasolutions.finance.application.port.`in`.company

import com.fasolutions.finance.application.domain.CompanyEvaluations

interface EditCompanyEvaluationsUseCase {
    fun editEvaluations(command: EditCompanyEvaluationsCommand): CompanyEvaluations
}
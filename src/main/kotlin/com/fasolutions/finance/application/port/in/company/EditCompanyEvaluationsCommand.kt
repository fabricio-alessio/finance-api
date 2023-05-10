package com.fasolutions.finance.application.port.`in`.company

import com.fasolutions.finance.application.domain.CompanyEvaluations

data class EditCompanyEvaluationsCommand(
    val code: String,
    val companyEvaluations: CompanyEvaluations
)

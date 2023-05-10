package com.fasolutions.finance.application.domain

data class Company(
    val code: String,
    var indicators: CompanyIndicators?,
    var evaluations: CompanyEvaluations?,
    var positionHistory: CompanyPositionHistory?
)

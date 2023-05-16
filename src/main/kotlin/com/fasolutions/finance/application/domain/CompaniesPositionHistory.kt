package com.fasolutions.finance.application.domain

data class CompaniesPositionHistory(
    val positions: Map<String, CompanyPositionHistory.Position>
)

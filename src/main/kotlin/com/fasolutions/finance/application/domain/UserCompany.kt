package com.fasolutions.finance.application.domain

import java.util.UUID

data class UserCompany(
    val id: UUID,
    val userId: Int,
    val code: String,
    var evaluations: CompanyEvaluations?,
    var positionHistory: CompanyPositionHistory?
) {
    data class UserCompanyId(
        val userId: Int,
        val code: String
    )
}

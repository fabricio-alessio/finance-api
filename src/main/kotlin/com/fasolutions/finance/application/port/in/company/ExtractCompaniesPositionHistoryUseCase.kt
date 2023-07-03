package com.fasolutions.finance.application.port.`in`.company

interface ExtractCompaniesPositionHistoryUseCase {
    fun extractWithBearer(userId: Int, bearer: String)
}

package com.fasolutions.finance.application.port.out.company

import com.fasolutions.finance.application.domain.CompaniesPositionHistory

interface CompaniesPositionHistoryExtractPort {
    fun extract(bearer: String): CompaniesPositionHistory
}

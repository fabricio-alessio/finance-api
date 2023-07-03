package com.fasolutions.finance.application.port.out.company

import com.fasolutions.finance.application.domain.CompanyIndicatorHistory

interface CompanyIndicatorHistoryExtractPort {
    fun extractByCode(code: String): CompanyIndicatorHistory?
}

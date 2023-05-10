package com.fasolutions.finance.application.port.out.company

import com.fasolutions.finance.application.domain.CompanyPriceHistory

interface CompanyPriceHistoryExtractPort {
    fun extractByCode(code: String): CompanyPriceHistory
}
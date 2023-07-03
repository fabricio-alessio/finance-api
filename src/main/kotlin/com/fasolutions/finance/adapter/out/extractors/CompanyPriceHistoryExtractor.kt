package com.fasolutions.finance.adapter.out.extractors

import com.fasolutions.finance.adapter.out.integration.statusinvest.client.CompanyPricesClient
import com.fasolutions.finance.adapter.out.integration.statusinvest.mapper.CompanyPricesMapper
import com.fasolutions.finance.application.domain.CompanyPriceHistory
import com.fasolutions.finance.application.port.out.company.CompanyPriceHistoryExtractPort
import org.springframework.stereotype.Component

@Component
class CompanyPriceHistoryExtractor(
    private val pricesClient: CompanyPricesClient = CompanyPricesClient(),
    private val pricesMapper: CompanyPricesMapper = CompanyPricesMapper()
) : CompanyPriceHistoryExtractPort {
    override fun extractByCode(code: String): CompanyPriceHistory {
        val sixMonthsResponse = pricesClient.callForCompany(code, CompanyPricesClient.Type.SIX_MONTHS)
        return pricesMapper.map(sixMonthsResponse)
    }
}

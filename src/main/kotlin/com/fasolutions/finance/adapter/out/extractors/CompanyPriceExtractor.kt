package com.fasolutions.finance.adapter.out.extractors

import com.fasolutions.finance.adapter.out.integration.statusinvest.client.CompanyPricesClient
import com.fasolutions.finance.adapter.out.integration.statusinvest.mapper.CompanyPriceMapper
import com.fasolutions.finance.application.port.out.company.CompanyPriceExtractPort
import org.springframework.stereotype.Component

@Component
class CompanyPriceExtractor(
    private val pricesClient: CompanyPricesClient = CompanyPricesClient(),
    private val priceMapper: CompanyPriceMapper = CompanyPriceMapper()
) : CompanyPriceExtractPort {
    override fun extractByCode(code: String): Double {
        val fiveDaysResponse = pricesClient.callForCompany(code, CompanyPricesClient.Type.FIVE_DAYS)
        return priceMapper.map(fiveDaysResponse)
    }
}
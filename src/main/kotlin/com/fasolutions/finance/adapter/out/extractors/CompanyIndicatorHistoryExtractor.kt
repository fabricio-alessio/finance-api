package com.fasolutions.finance.adapter.out.extractors

import com.fasolutions.finance.adapter.out.integration.statusinvest.client.CompanyIndicatorHistoryClient
import com.fasolutions.finance.adapter.out.integration.statusinvest.mapper.CompanyIndicatorHistoryMapper
import com.fasolutions.finance.application.domain.CompanyIndicatorHistory
import com.fasolutions.finance.application.port.out.company.CompanyIndicatorHistoryExtractPort
import org.springframework.stereotype.Component

@Component
class CompanyIndicatorHistoryExtractor(
    private val indicatorClient: CompanyIndicatorHistoryClient = CompanyIndicatorHistoryClient(),
    private val indicatorMapper: CompanyIndicatorHistoryMapper = CompanyIndicatorHistoryMapper()
): CompanyIndicatorHistoryExtractPort {
    override fun extractByCode(code: String): CompanyIndicatorHistory {
        val indicatorResponse = indicatorClient.callForCompany(code)
        return indicatorMapper.map(indicatorResponse)
    }
}
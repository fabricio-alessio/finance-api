package com.fasolutions.finance.adapter.out.extractors

import com.fasolutions.finance.adapter.out.integration.nuinvest.client.CustodyPositionClient
import com.fasolutions.finance.adapter.out.integration.nuinvest.mapper.CompanyPositionsMapper
import com.fasolutions.finance.application.domain.CompaniesPositionHistory
import com.fasolutions.finance.application.port.out.company.CompaniesPositionHistoryExtractPort
import org.springframework.stereotype.Component

@Component
class CompaniesPositionHistoryExtractor(
    private val client: CustodyPositionClient = CustodyPositionClient(),
    private val positionsMapper: CompanyPositionsMapper = CompanyPositionsMapper()
) : CompaniesPositionHistoryExtractPort {
    override fun extract(bearer: String): CompaniesPositionHistory {
        val response = client.call(bearer)
        return positionsMapper.map(response)
    }
}
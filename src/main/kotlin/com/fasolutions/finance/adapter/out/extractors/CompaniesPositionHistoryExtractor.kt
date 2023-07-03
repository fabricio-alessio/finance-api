package com.fasolutions.finance.adapter.out.extractors

import com.fasolutions.finance.adapter.out.integration.nuinvest.client.CustodyPositionClient
import com.fasolutions.finance.adapter.out.integration.nuinvest.client.InvestmentAveragePriceClient
import com.fasolutions.finance.adapter.out.integration.nuinvest.mapper.CompanyPositionsMapper
import com.fasolutions.finance.adapter.out.integration.nuinvest.model.CustodyPositionResponse
import com.fasolutions.finance.application.domain.CompaniesPositionHistory
import com.fasolutions.finance.application.port.out.company.CompaniesPositionHistoryExtractPort
import org.springframework.stereotype.Component

@Component
class CompaniesPositionHistoryExtractor(
    private val positionClient: CustodyPositionClient = CustodyPositionClient(),
    private val averagePriceClient: InvestmentAveragePriceClient = InvestmentAveragePriceClient(),
    private val positionsMapper: CompanyPositionsMapper = CompanyPositionsMapper()
) : CompaniesPositionHistoryExtractPort {
    override fun extract(bearer: String): CompaniesPositionHistory {
        val response = positionClient.call(bearer)
        val investmentsWithAveragePrice = response.investments
            .filter { it.isStock() }
            .map { investment ->
                val investmentAveragePrice = averagePriceClient.call(bearer, investment.stockCode!!)
                investment.copy(
                    averagePrice = investmentAveragePrice.averagePrice
                )
            }
        val newResponse = CustodyPositionResponse(
            investments = investmentsWithAveragePrice
        )
        return positionsMapper.map(newResponse)
    }
}

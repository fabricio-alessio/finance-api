package com.fasolutions.finance.adapter.out.extractors

import com.fasolutions.finance.adapter.out.integration.nuinvest.client.CustodyPositionClient
import com.fasolutions.finance.adapter.out.integration.nuinvest.client.InvestmentAveragePriceClient
import com.fasolutions.finance.adapter.out.integration.nuinvest.mapper.CompanyPositionsMapper
import com.fasolutions.finance.adapter.out.integration.nuinvest.model.CustodyPositionResponse
import com.fasolutions.finance.adapter.out.integration.nuinvest.model.InvestmentAveragePrice
import com.fasolutions.finance.application.domain.CompaniesPositionHistory
import com.fasolutions.finance.application.port.out.company.CompaniesPositionHistoryExtractPort
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class CompaniesPositionHistoryExtractor(
    private val positionClient: CustodyPositionClient = CustodyPositionClient(),
    private val averagePriceClient: InvestmentAveragePriceClient = InvestmentAveragePriceClient(),
    private val positionsMapper: CompanyPositionsMapper = CompanyPositionsMapper()
) : CompaniesPositionHistoryExtractPort {

    private val log = KotlinLogging.logger { }

    override fun extract(bearer: String): CompaniesPositionHistory {
        val response = positionClient.call(bearer)
        val investmentsWithAveragePrice = response.investments
            .filter { it.isStock() }
            .map { investment ->
                val investmentAveragePrice = callAveragePriceOrElseDefault(bearer, investment.stockCode!!)
                investment.copy(
                    averagePrice = investmentAveragePrice.averagePrice
                )
            }
        val newResponse = CustodyPositionResponse(
            investments = investmentsWithAveragePrice
        )
        return positionsMapper.map(newResponse)
    }

    private fun callAveragePriceOrElseDefault(bearer: String, stockCode: String): InvestmentAveragePrice {

        try {
            return averagePriceClient.call(bearer, stockCode)
        } catch (ex: IOException) {
            log.error(ex) {
                "Impossible to get average price for stock $stockCode. Returning default 0 (zero)"
            }
            return InvestmentAveragePrice(
                averagePrice = 0.0
            )
        }
    }
}

package com.fasolutions.finance.adapter.out.integration.nuinvest.mapper

import com.fasolutions.finance.adapter.out.integration.nuinvest.model.CustodyPositionResponse
import com.fasolutions.finance.application.domain.CompaniesPositionHistory
import com.fasolutions.finance.application.domain.CompanyPositionHistory
import com.fasolutions.finance.application.domain.SimpleDate

class CompanyPositionsMapper {

    fun map(positionResponse: CustodyPositionResponse): CompaniesPositionHistory {
        val stockInvestments = positionResponse.investments.filter { it.isStock() }
        val history = mutableMapOf<String, CompanyPositionHistory.Position>()
        stockInvestments.forEach {
            val code = it.stockCode!!
            history[code] = mapInvestment(it)
        }
        return CompaniesPositionHistory(history.toMap())
    }

    private fun mapInvestment(investment: CustodyPositionResponse.Investment) =
        CompanyPositionHistory.Position(
            currentPrice = investment.lastPrice,
            totalQuantity = investment.totalQuantity.toInt(),
            averagePrice = investment.averagePrice,
            date = SimpleDate.now()
        )
}
package com.fasolutions.finance.adapter.out.integration.nuinvest.mapper

import com.fasolutions.finance.adapter.out.integration.nuinvest.model.CustodyPositionResponse
import com.fasolutions.finance.application.domain.CompaniesPositionHistory
import com.fasolutions.finance.application.domain.CompanyPositionHistory
import com.fasolutions.finance.application.domain.SimpleDate

class CompanyPositionsMapper {

    companion object {
        const val STOCK_TYPE_ID = 7
    }

    fun map(positionResponse: CustodyPositionResponse): CompaniesPositionHistory {
        val stockInvestments = positionResponse.investments.filter(this::isStock)
        val history = mutableMapOf<String, CompanyPositionHistory>()
        stockInvestments.forEach {
            val code = it.stockCode!!
            val positionHistory = mapInvestment(it)
            history[code] = positionHistory
        }
        return CompaniesPositionHistory(history.toMap())
    }

    private fun mapInvestment(investment: CustodyPositionResponse.Investment) =
        CompanyPositionHistory(
            positions = mutableListOf(
                CompanyPositionHistory.Position(
                    currentPrice = investment.lastPrice,
                    totalQuantity = investment.totalQuantity.toInt(),
                    averagePrice = investment.averagePrice,
                    date = SimpleDate.now()
                )
            )
        )

    private fun isStock(investment: CustodyPositionResponse.Investment): Boolean {
        return investment.stockCode != null && investment.investmentType.id == STOCK_TYPE_ID
    }
}
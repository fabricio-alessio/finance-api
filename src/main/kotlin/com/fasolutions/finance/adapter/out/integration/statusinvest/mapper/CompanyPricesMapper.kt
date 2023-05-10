package com.fasolutions.finance.adapter.out.integration.statusinvest.mapper

import com.fasolutions.finance.adapter.out.integration.statusinvest.model.CompanyPricesResponse
import com.fasolutions.finance.adapter.out.integration.statusinvest.model.Price
import com.fasolutions.finance.application.domain.CompanyPriceHistory
import java.text.SimpleDateFormat

class CompanyPricesMapper {

    companion object {
        val formatter = SimpleDateFormat("dd/MM/yy")
    }

    fun map(response: CompanyPricesResponse): CompanyPriceHistory {
        return CompanyPriceHistory(
            prices = mapPrices(response.prices)
        )
    }

    private fun mapPrices(prices: List<Price>): List<CompanyPriceHistory.Price> {
        return prices.map {
            CompanyPriceHistory.Price(
                value = it.price,
                date = formatter.parse(it.date.substring(0, 8))
            )
        }
    }
}
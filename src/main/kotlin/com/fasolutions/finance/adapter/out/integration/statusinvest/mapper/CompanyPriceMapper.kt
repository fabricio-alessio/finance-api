package com.fasolutions.finance.adapter.out.integration.statusinvest.mapper

import com.fasolutions.finance.adapter.out.integration.statusinvest.model.CompanyPricesResponse
import com.fasolutions.finance.adapter.out.integration.statusinvest.model.Price

class CompanyPriceMapper {

    fun map(response: CompanyPricesResponse): Double {
        return mapPrice(response.prices)
    }

    private fun mapPrice(prices: List<Price>): Double {
        return try {
            prices.last().price
        } catch (ex: NoSuchElementException) {
            0.0
        }
    }
}
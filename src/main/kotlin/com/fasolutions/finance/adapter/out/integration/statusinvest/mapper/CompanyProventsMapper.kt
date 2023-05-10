package com.fasolutions.finance.adapter.out.integration.statusinvest.mapper

import com.fasolutions.finance.adapter.out.integration.statusinvest.model.AssetEarningsYearlyModel
import com.fasolutions.finance.adapter.out.integration.statusinvest.model.CompanyProventsResponse
import com.fasolutions.finance.application.domain.CompanyProvents

class CompanyProventsMapper {

    fun map(response: CompanyProventsResponse): CompanyProvents {
        return CompanyProvents(
            yearProvents = mapEarningYears(response.assetEarningsYearlyModels)
        )
    }

    private fun mapEarningYears(yearlyModels: List<AssetEarningsYearlyModel>): Map<Int, Double> {
        val rankMap = yearlyModels.associateBy { it.rank }
        val map = mutableMapOf<Int, Double>()
        rankMap.forEach { rank ->
            map[rank.key] = rank.value.value
        }
        return map
    }
}
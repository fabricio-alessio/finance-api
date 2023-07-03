package com.fasolutions.finance.adapter.out.company

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.Date

@Document("companies")
@TypeAlias("companies")
data class CompanyDoc(
    @Id
    val code: String,
    val indicators: Indicators?,
    @CreatedDate
    val createdAt: LocalDateTime,
) {
    data class Indicators(
        val price: Double,
        val basics: Basics,
        val indicatorHistory: IndicatorHistory,
        val priceHistory: PriceHistory,
        val provents: Provents
    )

    data class Basics(
        val sector: String,
        val subSector: String,
        val type: String,
        val name: String
    )

    data class Evaluations(
        val observedPayout: Double,
        val proventPredictions: List<ProventPrediction>
    )

    data class ProventPrediction(
        val year: Int,
        val value: Double
    )

    data class IndicatorHistory(
        val indicators: List<Indicator>
    )

    data class Indicator(
        val code: String,
        val actual: Double,
        val average: Double,
        val ranks: List<Rank>
    )

    data class Rank(
        val rank: Int,
        val value: Double
    )

    data class PositionHistory(
        val positions: List<Position>
    )

    data class Position(
        val currentPrice: Double,
        val totalQuantity: Int,
        val averagePrice: Double,
        val date: String
    )

    data class PriceHistory(
        val prices: List<Price>
    )

    data class Price(
        val value: Double,
        val date: Date
    )

    data class Provents(
        val yearProvents: Map<Int, Double>
    )
}

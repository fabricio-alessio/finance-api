package com.fasolutions.finance.adapter.out.company

import com.fasolutions.finance.application.domain.Company
import com.fasolutions.finance.application.domain.CompanyBasics
import com.fasolutions.finance.application.domain.CompanyEvaluations
import com.fasolutions.finance.application.domain.CompanyIndicatorHistory
import com.fasolutions.finance.application.domain.CompanyIndicators
import com.fasolutions.finance.application.domain.CompanyPositionHistory
import com.fasolutions.finance.application.domain.CompanyPriceHistory
import com.fasolutions.finance.application.domain.CompanyProvents
import com.fasolutions.finance.application.domain.IndicatorCode
import com.fasolutions.finance.application.domain.SimpleDate
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CompanyDocMapper {

    fun backward(source: CompanyDoc) =
        Company(
            code = source.code,
            indicators = source.indicators?.let(this::backwardIndicators)
        )

    private fun backwardIndicators(source: CompanyDoc.Indicators) =
        CompanyIndicators(
            price = source.price,
            basics = backwardBasics(source.basics),
            indicatorHistory = backwardIndicatorHistory(source.indicatorHistory),
            priceHistory = backwardPriceHistory(source.priceHistory),
            provents = backwardProvents(source.provents)
        )

    private fun backwardProvents(source: CompanyDoc.Provents) =
        CompanyProvents(
            yearProvents = source.yearProvents
        )

    private fun backwardPriceHistory(source: CompanyDoc.PriceHistory) =
        CompanyPriceHistory(
            prices = source.prices.map(this::backwardPrice)
        )

    private fun backwardPrice(source: CompanyDoc.Price) =
        CompanyPriceHistory.Price(
            value = source.value,
            date = source.date
        )

    private fun backwardPositionHistory(source: CompanyDoc.PositionHistory) =
        CompanyPositionHistory(
            positions = source.positions.map(this::backwardPosition).toMutableList()
        )

    private fun backwardPosition(source: CompanyDoc.Position) =
        CompanyPositionHistory.Position(
            currentPrice = source.currentPrice,
            totalQuantity = source.totalQuantity,
            averagePrice = source.averagePrice,
            date = SimpleDate(source.date)
        )

    private fun backwardIndicatorHistory(source: CompanyDoc.IndicatorHistory) =
        CompanyIndicatorHistory(
            indicators = source.indicators.map(this::backwardIndicatorsOfHistory)
        )

    private fun backwardIndicatorsOfHistory(source: CompanyDoc.Indicator) =
        CompanyIndicatorHistory.Indicator(
            code = IndicatorCode.valueOf(source.code),
            actual = source.actual,
            average = source.average,
            ranks = source.ranks.map(this::backwardRank)
        )

    private fun backwardRank(source: CompanyDoc.Rank) =
        CompanyIndicatorHistory.Rank(
            rank = source.rank,
            value = source.value
        )

    private fun backwardEvaluations(evaluations: CompanyDoc.Evaluations) =
        CompanyEvaluations(
            observedPayout = evaluations.observedPayout,
            proventPredictions = evaluations.proventPredictions.map(this::backwardProventsPrediction)
        )

    private fun backwardProventsPrediction(source: CompanyDoc.ProventPrediction) =
        CompanyEvaluations.ProventPrediction(
            year = source.year,
            value = source.value
        )

    private fun backwardBasics(source: CompanyDoc.Basics) =
        CompanyBasics(
            sector = source.sector,
            subSector = source.subSector,
            type = source.type,
            name = source.name
        )

    fun forward(source: Company) =
        CompanyDoc(
            code = source.code,
            indicators = source.indicators?.let(this::forwardIndicators),
            createdAt = LocalDateTime.now()
        )

    private fun forwardIndicators(source: CompanyIndicators) =
        CompanyDoc.Indicators(
            price = source.price,
            basics = forwardBasics(source.basics),
            indicatorHistory = forwardIndicatorHistory(source.indicatorHistory),
            priceHistory = forwardPriceHistory(source.priceHistory),
            provents = forwardProvents(source.provents)
        )

    private fun forwardProvents(source: CompanyProvents) =
        CompanyDoc.Provents(
            yearProvents = source.yearProvents
        )

    private fun forwardPriceHistory(source: CompanyPriceHistory) =
        CompanyDoc.PriceHistory(
            prices = source.prices.map(this::forwardPrice)
        )

    private fun forwardPrice(source: CompanyPriceHistory.Price) =
        CompanyDoc.Price(
            value = source.value,
            date = source.date
        )

    private fun forwardPositionHistory(source: CompanyPositionHistory) =
        CompanyDoc.PositionHistory(
            positions = source.positions.map(this::forwardPosition)
        )

    private fun forwardPosition(source: CompanyPositionHistory.Position) =
        CompanyDoc.Position(
            currentPrice = source.currentPrice,
            totalQuantity = source.totalQuantity,
            averagePrice = source.averagePrice,
            date = source.date.value
        )

    private fun forwardIndicatorHistory(source: CompanyIndicatorHistory) =
        CompanyDoc.IndicatorHistory(
            indicators = source.indicators.map(this::forwardIndicatorOfHistory)
        )

    private fun forwardIndicatorOfHistory(source: CompanyIndicatorHistory.Indicator) =
        CompanyDoc.Indicator(
            code = source.code.name,
            actual = source.actual,
            average = source.average,
            ranks = source.ranks.map(this::forwardRank)
        )

    private fun forwardRank(source: CompanyIndicatorHistory.Rank) =
        CompanyDoc.Rank(
            rank = source.rank,
            value = source.value
        )

    private fun forwardEvaluations(source: CompanyEvaluations) =
        CompanyDoc.Evaluations(
            observedPayout = source.observedPayout,
            proventPredictions = source.proventPredictions.map(this::forwardProventPrediction)
        )

    private fun forwardProventPrediction(source: CompanyEvaluations.ProventPrediction) =
        CompanyDoc.ProventPrediction(
            year = source.year,
            value = source.value
        )

    fun forwardBasics(source: CompanyBasics) =
        CompanyDoc.Basics(
            sector = source.sector,
            subSector = source.subSector,
            type = source.type,
            name = source.name
        )
}

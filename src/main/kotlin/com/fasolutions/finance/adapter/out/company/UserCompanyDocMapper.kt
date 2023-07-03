package com.fasolutions.finance.adapter.out.company

import com.fasolutions.finance.application.domain.CompanyEvaluations
import com.fasolutions.finance.application.domain.CompanyPositionHistory
import com.fasolutions.finance.application.domain.SimpleDate
import com.fasolutions.finance.application.domain.UserCompany
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

@Component
class UserCompanyDocMapper {

    fun backward(source: UserCompanyDoc) =
        UserCompany(
            id = UUID.fromString(source.id),
            userId = source.userId,
            code = source.code,
            evaluations = source.evaluations?.let(this::backwardEvaluations),
            positionHistory = source.positionHistory?.let(this::backwardPositionHistory)
        )

    fun backwardId(source: UserCompanyDoc.UserCompanyId) =
        UserCompany.UserCompanyId(
            userId = source.userId,
            code = source.code
        )

    private fun backwardPositionHistory(source: UserCompanyDoc.PositionHistory) =
        CompanyPositionHistory(
            positions = source.positions.map(this::backwardPosition).toMutableList()
        )

    private fun backwardPosition(source: UserCompanyDoc.Position) =
        CompanyPositionHistory.Position(
            currentPrice = source.currentPrice,
            totalQuantity = source.totalQuantity,
            averagePrice = source.averagePrice,
            date = SimpleDate(source.date)
        )

    private fun backwardEvaluations(evaluations: UserCompanyDoc.Evaluations) =
        CompanyEvaluations(
            observedPayout = evaluations.observedPayout,
            proventPredictions = evaluations.proventPredictions.map(this::backwardProventsPrediction)
        )

    private fun backwardProventsPrediction(source: UserCompanyDoc.ProventPrediction) =
        CompanyEvaluations.ProventPrediction(
            year = source.year,
            value = source.value
        )

    fun forward(source: UserCompany) =
        UserCompanyDoc(
            id = source.id.toString(),
            userId = source.userId,
            code = source.code,
            evaluations = source.evaluations?.let(this::forwardEvaluations),
            positionHistory = source.positionHistory?.let(this::forwardPositionHistory),
            createdAt = LocalDateTime.now()
        )

    fun forwardId(source: UserCompany.UserCompanyId) =
        UserCompanyDoc.UserCompanyId(
            userId = source.userId,
            code = source.code
        )

    private fun forwardPositionHistory(source: CompanyPositionHistory) =
        UserCompanyDoc.PositionHistory(
            positions = source.positions.map(this::forwardPosition)
        )

    private fun forwardPosition(source: CompanyPositionHistory.Position) =
        UserCompanyDoc.Position(
            currentPrice = source.currentPrice,
            totalQuantity = source.totalQuantity,
            averagePrice = source.averagePrice,
            date = source.date.value
        )

    private fun forwardEvaluations(source: CompanyEvaluations) =
        UserCompanyDoc.Evaluations(
            observedPayout = source.observedPayout,
            proventPredictions = source.proventPredictions.map(this::forwardProventPrediction)
        )

    private fun forwardProventPrediction(source: CompanyEvaluations.ProventPrediction) =
        UserCompanyDoc.ProventPrediction(
            year = source.year,
            value = source.value
        )
}

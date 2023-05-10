package com.fasolutions.finance.adapter.`in`.company

import com.fasolutions.finance.application.domain.CompanyEvaluations
import org.springframework.stereotype.Component

@Component
class CompanyEvaluationsExternalMapper {

    fun backward(source: CompanyEvaluationsExternal) =
        CompanyEvaluations(
            observedPayout = source.observedPayout,
            proventPredictions = source.proventPredictions.map(this::backwardProventPrediction)
        )

    private fun backwardProventPrediction(source: CompanyEvaluationsExternal.ProventPrediction) =
        CompanyEvaluations.ProventPrediction(
            year = source.year,
            value = source.value
        )

    fun forward(source: CompanyEvaluations) =
        CompanyEvaluationsExternal(
            observedPayout = source.observedPayout,
            proventPredictions = source.proventPredictions.map(this::forwardProventPrediction)
        )

    private fun forwardProventPrediction(source: CompanyEvaluations.ProventPrediction) =
        CompanyEvaluationsExternal.ProventPrediction(
            year = source.year,
            value = source.value
        )
}

package com.fasolutions.finance.adapter.`in`.company

data class CompanyEvaluationsExternal(
    val observedPayout: Double,
    val proventPredictions: List<ProventPrediction>
) {
    data class ProventPrediction(
        val year: Int,
        val value: Double
    )
}

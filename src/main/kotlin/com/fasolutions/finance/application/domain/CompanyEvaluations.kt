package com.fasolutions.finance.application.domain

data class CompanyEvaluations(
    val observedPayout: Double,
    val proventPredictions: List<ProventPrediction>
) {
    data class ProventPrediction(
        val year: Int,
        val value: Double
    )

    fun averageNextProvents(): Double {
        return if (proventPredictions.isEmpty()) {
            0.0
        } else {
            proventPredictions.sumOf { it.value } / proventPredictions.size
        }
    }
}

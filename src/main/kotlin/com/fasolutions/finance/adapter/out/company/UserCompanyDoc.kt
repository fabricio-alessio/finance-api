package com.fasolutions.finance.adapter.out.company

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("userCompanies")
@TypeAlias("userCompanies")
data class UserCompanyDoc(
    @Id
    val id: String,
    val userId: Int,
    val code: String,
    val evaluations: Evaluations?,
    val positionHistory: PositionHistory?,
    @CreatedDate
    val createdAt: LocalDateTime,
) {
    data class UserCompanyId(
        val userId: Int,
        val code: String
    )

    data class Evaluations(
        val observedPayout: Double,
        val proventPredictions: List<ProventPrediction>
    )

    data class ProventPrediction(
        val year: Int,
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
}

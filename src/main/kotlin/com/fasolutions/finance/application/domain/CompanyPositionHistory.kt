package com.fasolutions.finance.application.domain

data class CompanyPositionHistory(
    val positions: List<Position>
) {
    data class Position(
        val currentPrice: Double,
        val totalQuantity: Int,
        val averagePrice: Double,
        val date: SimpleDate
    )
/*
    fun changePositionByDate(position: Position) {
        positions.removeIf { it.date == position.date }
        positions.add(position)
    }*/
}

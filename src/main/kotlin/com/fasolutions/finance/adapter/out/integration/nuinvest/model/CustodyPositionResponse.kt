package com.fasolutions.finance.adapter.out.integration.nuinvest.model

data class CustodyPositionResponse(
    val investments: List<Investment>
) {

    data class Investment(
        val stockCode: String?,
        val financialCurrentValue: Double,
        val lastPrice: Double,
        val totalQuantity: Double,
        val averagePrice: Double,
        val securityType: String,
        val investmentType: InvestmentType
    ) {
        companion object {
            const val STOCK_TYPE_ID = 7
        }

        fun isStock(): Boolean {
            return stockCode != null && investmentType.id == STOCK_TYPE_ID
        }
    }

    data class InvestmentType(
        val id: Int
    )
}

package com.fasolutions.finance.application.port.out.indicator

interface GetIndicatorCodesPort {
    fun findAll(): List<String>
}
package com.fasolutions.finance.adapter.out.integration.nuinvest.client

import com.fasolutions.finance.adapter.out.integration.nuinvest.model.InvestmentAveragePrice
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

class InvestmentAveragePriceClient {
    private val client = OkHttpClient.Builder()
        .hostnameVerifier(HostnameVerifier { _, _ -> true }) // To avoid error -> Hostname www.nuinvest.com.br not verified (no certificates)
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .build()

    private val mapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun call(bearer: String, stockCode: String): InvestmentAveragePrice {

        val url = "https://www.nuinvest.com.br/api/gringott/averagePrice/$stockCode"
        val request = Request.Builder()
            .url(url)
            .get()
            .header("authorization", bearer)
            .build()

        val response = client.newCall(request).execute()

        if (response.code == 200) {
            val body = response.body!!.string()
            println(body)
            return mapper.readValue(body, InvestmentAveragePrice::class.java)
        } else {
            throw IOException("Unexpected code ${response.code} url $url and response ${response.body!!.string()}")
        }
    }
}

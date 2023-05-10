package com.fasolutions.finance.adapter.out.extractors

import com.fasolutions.finance.adapter.out.integration.statusinvest.client.CompanyProventsClient
import com.fasolutions.finance.adapter.out.integration.statusinvest.mapper.CompanyProventsMapper
import com.fasolutions.finance.application.domain.CompanyProvents
import com.fasolutions.finance.application.port.out.company.CompanyProventsExtractPort
import org.springframework.stereotype.Component

@Component
class CompanyProventsExtractor(
    private val proventsClient: CompanyProventsClient = CompanyProventsClient(),
    private val proventsMapper: CompanyProventsMapper = CompanyProventsMapper()
) : CompanyProventsExtractPort {
    override fun extractByCode(code: String): CompanyProvents {
        val proventsResponse = proventsClient.callForCompany(code)
        return proventsMapper.map(proventsResponse)
    }
}
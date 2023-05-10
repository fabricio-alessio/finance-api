package com.fasolutions.finance.adapter.out.extractors

import com.fasolutions.finance.adapter.out.integration.fundamentus.client.BasicsClient
import com.fasolutions.finance.adapter.out.integration.fundamentus.mapper.BasicsMapper
import com.fasolutions.finance.application.domain.CompanyBasics
import com.fasolutions.finance.application.port.out.company.CompanyBasicsExtractPort
import org.springframework.stereotype.Component

@Component
class CompanyBasicsExtractor(
    private val client: BasicsClient = BasicsClient(),
    private val mapper: BasicsMapper = BasicsMapper()
) : CompanyBasicsExtractPort{
    override fun extractByCode(code: String): CompanyBasics {
        val response = client.call(code)
        return mapper.map(response)
    }
}
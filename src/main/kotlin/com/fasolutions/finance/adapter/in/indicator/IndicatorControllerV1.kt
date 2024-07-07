package com.fasolutions.finance.adapter.`in`.indicator

import com.fasolutions.finance.application.port.`in`.company.ExtractCompanyIndicatorsUseCase
import com.fasolutions.finance.application.port.out.indicator.GetIndicatorCodesPort
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/indicators")
@Tag(name = "Indicators")
class IndicatorControllerV1(
    private val extractIndicatorsUseCase: ExtractCompanyIndicatorsUseCase,
    private val getIndicatorCodesPort: GetIndicatorCodesPort
) {

    private val log = KotlinLogging.logger { }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Extract an indicator by code")
    @PostMapping(
        value = ["/{code}/extract"]
    )
    fun extractByCode(@PathVariable code: String) {
        log.info { "Extract $code indicator" }
        extractIndicatorsUseCase.extractByCode(code)
    }

    @Operation(summary = "Get all indicators codes")
    @GetMapping(
        value = ["/codes"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getAllCodes() = IndicatorCodesExternal(getIndicatorCodesPort.findAll())
}

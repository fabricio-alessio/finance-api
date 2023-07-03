package com.fasolutions.finance.adapter.`in`.positions

import com.fasolutions.finance.application.port.`in`.company.ExtractCompaniesPositionHistoryUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/positions")
@Tag(name = "Positions")
class PositionsControllerV1(
    private val extractPositionHistoryUseCase: ExtractCompaniesPositionHistoryUseCase
) {

    private val log = KotlinLogging.logger { }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Extract companies positions")
    @PostMapping(
        value = [""]
    )
    fun extract(
        @RequestBody positionsRequest: PositionsRequest,
        @RequestHeader userId: Int
    ) {
        log.info { "Extract positions for userId $userId with $positionsRequest" }
        extractPositionHistoryUseCase.extractWithBearer(userId, positionsRequest.bearer)
    }
}

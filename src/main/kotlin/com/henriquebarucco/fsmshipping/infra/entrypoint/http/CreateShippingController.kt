package com.henriquebarucco.fsmshipping.infra.entrypoint.http

import com.henriquebarucco.fsmshipping.domain.shipping.interactor.CreateShippingInteractor
import com.henriquebarucco.fsmshipping.infra.entrypoint.http.dto.request.CreateShippingRequest
import com.henriquebarucco.fsmshipping.infra.entrypoint.http.dto.response.CreateShippingResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/shippings")
class CreateShippingController(
    private val createShippingInteractor: CreateShippingInteractor,
) {
    @PostMapping
    fun createShipping(
        @RequestBody request: CreateShippingRequest,
    ): ResponseEntity<CreateShippingResponse> {
        val shipping =
            this.createShippingInteractor.execute(
                CreateShippingInteractor.Input(
                    uuid = request.uuid,
                    name = request.name,
                ),
            )

        val response =
            CreateShippingResponse(
                id = shipping.id,
                uuid = shipping.uuid,
                name = shipping.name,
                state = shipping.state.name,
            )

        return ResponseEntity.ok(response)
    }
}

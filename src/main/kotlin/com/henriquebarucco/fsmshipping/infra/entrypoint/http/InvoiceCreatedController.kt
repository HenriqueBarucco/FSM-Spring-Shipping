package com.henriquebarucco.fsmshipping.infra.entrypoint.http

import com.henriquebarucco.fsmshipping.domain.shipping.entity.ShippingState
import com.henriquebarucco.fsmshipping.infra.entrypoint.fsm.ShippingEvent
import com.henriquebarucco.fsmshipping.infra.entrypoint.http.dto.request.InvoiceCreatedRequest
import org.springframework.http.ResponseEntity
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1/shippings")
class InvoiceCreatedController(
    private val stateMachineFactory: StateMachineFactory<ShippingState, ShippingEvent>,
) {
    @PostMapping("/{id}/invoice")
    fun createdInvoice(
        @PathVariable id: String,
        @RequestBody request: InvoiceCreatedRequest,
    ): ResponseEntity<Void> {
        println("Invoice created for shipping $id")
        val (invoice) = request

        val stateMachine = this.stateMachineFactory.getStateMachine(id)
        stateMachine.startReactively().subscribe()

        stateMachine
            .sendEvent(
                Mono.just(
                    MessageBuilder
                        .withPayload(ShippingEvent.INVOICE_CREATED)
                        .setHeader("shippingId", id)
                        .setHeader("invoice", invoice)
                        .build(),
                ),
            ).subscribe { result -> println("Result: ${result.resultType}") }

        return ResponseEntity.ok().build()
    }
}

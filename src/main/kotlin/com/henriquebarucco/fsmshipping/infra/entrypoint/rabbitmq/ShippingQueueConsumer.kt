package com.henriquebarucco.fsmshipping.infra.entrypoint.rabbitmq

import com.henriquebarucco.fsmshipping.domain.shipping.entity.ShippingState
import com.henriquebarucco.fsmshipping.domain.shipping.interactor.CreateShippingInteractor
import com.henriquebarucco.fsmshipping.domain.shipping.interactor.UpdateShippingInvoiceInteractor
import com.henriquebarucco.fsmshipping.infra.entrypoint.fsm.ShippingEvent
import com.henriquebarucco.fsmshipping.infra.entrypoint.rabbitmq.dto.MessageEvent
import com.henriquebarucco.fsmshipping.infra.entrypoint.rabbitmq.dto.ShippingMessageDto
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ShippingQueueConsumer(
    private val createShippingInteractor: CreateShippingInteractor,
    private val updateShippingInvoiceInteractor: UpdateShippingInvoiceInteractor,
    private val stateMachineFactory: StateMachineFactory<ShippingState, ShippingEvent>,
) {
    @RabbitListener(queues = ["\${rabbitmq.queues.shipping.queue}"], autoStartup = "true")
    fun consume(message: Message<ShippingMessageDto>) {
        val (id, event, data) = message.payload

        when (event) {
            MessageEvent.SHIPPING_CREATED -> {
                // Verify if the shipping already exists...
                // If not, create a new shipping
                this.createShippingInteractor.execute(CreateShippingInteractor.Input(id, data.name))
            }
            MessageEvent.INVOICE_CREATED -> {
                if (data.invoice == null) throw RuntimeException("Missing invoice")
                this.updateShippingInvoiceInteractor.execute(id, data.invoice)

                val stateMachine = this.stateMachineFactory.getStateMachine(id)
                stateMachine.startReactively().subscribe()

                stateMachine
                    .sendEvent(
                        Mono.just(
                            MessageBuilder
                                .withPayload(ShippingEvent.INVOICE_CREATED)
                                .setHeader("shippingId", id)
                                .build(),
                        ),
                    ).subscribe { result -> println("Result: ${result.resultType}") }
            }
        }
    }
}

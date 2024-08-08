package com.henriquebarucco.fsmshipping.infra.entrypoint.fsm

import com.henriquebarucco.fsmshipping.domain.shipping.entity.ShippingState
import com.henriquebarucco.fsmshipping.domain.shipping.interactor.UpdateShippingInvoiceInteractor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.action.Action
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import reactor.core.publisher.Mono
import java.util.UUID

@Configuration
@EnableStateMachineFactory
class ShippingStateMachineConfig(
    private val updateShippingInvoiceInteractor: UpdateShippingInvoiceInteractor,
) : StateMachineConfigurerAdapter<ShippingState, ShippingEvent>() {
    override fun configure(states: StateMachineStateConfigurer<ShippingState, ShippingEvent>) {
        states
            .withStates()
            .initial(ShippingState.CREATED)
            .state(ShippingState.INVOICE_CREATED, invoiceCreatedAction())
            .end(ShippingState.SENT)
    }

    override fun configure(transitions: StateMachineTransitionConfigurer<ShippingState, ShippingEvent>) {
        transitions
            .withExternal()
            .source(ShippingState.CREATED)
            .target(ShippingState.INVOICE_CREATED)
            .event(ShippingEvent.INVOICE_CREATED)
            .action(invoiceCreatedAction())
            .and()
            .withExternal()
            .source(ShippingState.INVOICE_CREATED)
            .target(ShippingState.SENT)
            .event(ShippingEvent.SEND)
            .action(sendAction())
    }

    @Bean
    fun invoiceCreatedAction(): Action<ShippingState, ShippingEvent> =
        Action { context ->
            val shippingId = context.messageHeaders["shippingId"] as UUID
            val invoice = context.messageHeaders["invoice"] as UUID

            println("caiu aqu")

            this.updateShippingInvoiceInteractor.execute(shippingId, invoice)

            context.stateMachine
                .sendEvent(
                    Mono.just(
                        MessageBuilder.withPayload(ShippingEvent.SEND).build(),
                    ),
                ).subscribe()
        }

    @Bean
    fun sendAction(): Action<ShippingState, ShippingEvent> =
        Action { context ->
            val shippingId = context.messageHeaders["shippingId"] as UUID

            // Simulate sending to some API
            println("Sending shipping $shippingId to some API ...")
        }
}

enum class ShippingEvent {
    INVOICE_CREATED,
    SEND,
}

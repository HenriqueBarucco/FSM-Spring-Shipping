package com.henriquebarucco.fsmshipping.infra.entrypoint.rabbitmq.dto

import java.util.UUID

data class ShippingMessageDto(
    val id: UUID,
    val event: MessageEvent,
    val data: ShippingDto,
)

data class ShippingDto(
    val name: String,
    val invoice: UUID? = null,
)

enum class MessageEvent {
    SHIPPING_CREATED,
    INVOICE_CREATED,
}

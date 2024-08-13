package com.henriquebarucco.fsmshipping.infra.entrypoint.scheduler.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MessageDto(
    @Contextual
    val id: UUID,
    val event: MessageEventDto,
    val data: MessageDataDto,
)

enum class MessageEventDto {
    SHIPPING_CREATED,
    INVOICE_CREATED,
}

@Serializable
data class MessageDataDto(
    val name: String,
    @Contextual
    val invoice: UUID? = null,
)

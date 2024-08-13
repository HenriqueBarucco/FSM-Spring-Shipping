package com.henriquebarucco.fsmshipping.domain.shipping.gateway

import com.henriquebarucco.fsmshipping.domain.shipping.entity.Shipping
import java.util.UUID

interface CreateShippingGateway {
    fun create(input: Input): Shipping

    data class Input(
        val uuid: UUID,
        val name: String,
    )
}

package com.henriquebarucco.fsmshipping.domain.shipping.gateway

import com.henriquebarucco.fsmshipping.domain.shipping.entity.Shipping

interface CreateShippingGateway {
    fun create(input: Input): Shipping

    data class Input(
        val name: String,
    )
}

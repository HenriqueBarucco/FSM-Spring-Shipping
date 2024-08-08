package com.henriquebarucco.fsmshipping.domain.shipping.entity

import java.util.UUID

data class Shipping(
    val id: UUID,
    val name: String,
    val state: ShippingState,
)

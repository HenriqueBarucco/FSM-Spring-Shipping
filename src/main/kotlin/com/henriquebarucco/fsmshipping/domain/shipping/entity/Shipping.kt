package com.henriquebarucco.fsmshipping.domain.shipping.entity

import java.util.UUID

data class Shipping(
    val id: Int,
    val uuid: UUID,
    val name: String,
    val state: ShippingState,
)

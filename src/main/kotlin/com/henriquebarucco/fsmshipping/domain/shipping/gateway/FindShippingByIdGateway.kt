package com.henriquebarucco.fsmshipping.domain.shipping.gateway

import com.henriquebarucco.fsmshipping.domain.shipping.entity.Shipping
import java.util.UUID

interface FindShippingByIdGateway {
    fun find(id: UUID): Shipping?
}

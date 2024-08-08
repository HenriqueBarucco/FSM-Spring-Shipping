package com.henriquebarucco.fsmshipping.domain.shipping.gateway

import java.util.UUID

interface UpdateShippingInvoiceGateway {
    fun update(
        shippingId: UUID,
        invoice: UUID,
    )
}

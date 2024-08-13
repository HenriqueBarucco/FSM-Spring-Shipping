package com.henriquebarucco.fsmshipping.domain.shipping.gateway

import java.util.UUID

interface UpdateShippingInvoiceGateway {
    fun update(
        shippingUuid: UUID,
        invoice: UUID,
    )
}

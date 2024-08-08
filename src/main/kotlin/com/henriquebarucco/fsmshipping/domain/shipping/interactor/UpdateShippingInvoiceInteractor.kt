package com.henriquebarucco.fsmshipping.domain.shipping.interactor

import java.util.UUID

interface UpdateShippingInvoiceInteractor {
    fun execute(
        shippingId: UUID,
        invoice: UUID,
    )
}

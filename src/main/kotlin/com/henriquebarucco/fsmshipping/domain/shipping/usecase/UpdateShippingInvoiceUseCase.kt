package com.henriquebarucco.fsmshipping.domain.shipping.usecase

import com.henriquebarucco.fsmshipping.domain.shipping.gateway.FindShippingByIdGateway
import com.henriquebarucco.fsmshipping.domain.shipping.gateway.UpdateShippingInvoiceGateway
import com.henriquebarucco.fsmshipping.domain.shipping.interactor.UpdateShippingInvoiceInteractor
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UpdateShippingInvoiceUseCase(
    private val findShippingByIdGateway: FindShippingByIdGateway,
    private val updateShippingInvoiceGateway: UpdateShippingInvoiceGateway,
) : UpdateShippingInvoiceInteractor {
    override fun execute(
        shippingId: UUID,
        invoice: UUID,
    ) {
        val shipping = this.findShippingByIdGateway.find(shippingId) ?: throw RuntimeException("Shipping not found")

        this.updateShippingInvoiceGateway.update(shipping.uuid, invoice)
    }
}

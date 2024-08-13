package com.henriquebarucco.fsmshipping.infra.dataprovider.database

import com.henriquebarucco.fsmshipping.domain.shipping.gateway.UpdateShippingInvoiceGateway
import com.henriquebarucco.fsmshipping.infra.dataprovider.database.entity.ShippingState
import com.henriquebarucco.fsmshipping.infra.dataprovider.database.repository.ShippingRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UpdateShippingInvoiceDataProvider(
    private val shippingRepository: ShippingRepository,
) : UpdateShippingInvoiceGateway {
    override fun update(
        shippingUuid: UUID,
        invoice: UUID,
    ) {
        val shippingEntity = this.shippingRepository.findByUuid(shippingUuid).orElse(null) ?: throw RuntimeException("Shipping not found")

        shippingEntity.invoice = invoice
        shippingEntity.state = ShippingState.INVOICE_CREATED

        this.shippingRepository.save(shippingEntity)
    }
}

package com.henriquebarucco.fsmshipping.infra.dataprovider.database

import com.henriquebarucco.fsmshipping.domain.shipping.entity.Shipping
import com.henriquebarucco.fsmshipping.domain.shipping.entity.ShippingState
import com.henriquebarucco.fsmshipping.domain.shipping.gateway.FindShippingByIdGateway
import com.henriquebarucco.fsmshipping.infra.dataprovider.database.repository.ShippingRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FindShippingByIdDataProvider(
    private val shippingRepository: ShippingRepository,
) : FindShippingByIdGateway {
    override fun find(id: UUID): Shipping? {
        val shippingEntity = this.shippingRepository.findById(id).orElse(null) ?: return null

        return Shipping(
            id = shippingEntity.id!!,
            uuid = shippingEntity.uuid,
            name = shippingEntity.name,
            state = ShippingState.valueOf(shippingEntity.state.name),
        )
    }
}

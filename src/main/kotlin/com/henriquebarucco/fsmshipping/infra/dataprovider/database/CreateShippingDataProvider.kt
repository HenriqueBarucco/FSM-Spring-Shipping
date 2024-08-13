package com.henriquebarucco.fsmshipping.infra.dataprovider.database

import com.henriquebarucco.fsmshipping.domain.shipping.entity.Shipping
import com.henriquebarucco.fsmshipping.domain.shipping.entity.ShippingState
import com.henriquebarucco.fsmshipping.domain.shipping.gateway.CreateShippingGateway
import com.henriquebarucco.fsmshipping.infra.dataprovider.database.entity.ShippingEntity
import com.henriquebarucco.fsmshipping.infra.dataprovider.database.repository.ShippingRepository
import org.springframework.stereotype.Service

@Service
class CreateShippingDataProvider(
    private val shippingRepository: ShippingRepository,
) : CreateShippingGateway {
    override fun create(input: CreateShippingGateway.Input): Shipping {
        val (uuid, name) = input

        val shippingEntity =
            ShippingEntity(
                uuid = uuid,
                name = name,
            )

        val savedShippingEntity = this.shippingRepository.save(shippingEntity)

        return Shipping(
            id = savedShippingEntity.id!!,
            uuid = savedShippingEntity.uuid,
            name = savedShippingEntity.name,
            state = ShippingState.valueOf(savedShippingEntity.state.name),
        )
    }
}

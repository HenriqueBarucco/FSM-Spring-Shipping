package com.henriquebarucco.fsmshipping.domain.shipping.usecase

import com.henriquebarucco.fsmshipping.domain.shipping.entity.Shipping
import com.henriquebarucco.fsmshipping.domain.shipping.gateway.CreateShippingGateway
import com.henriquebarucco.fsmshipping.domain.shipping.interactor.CreateShippingInteractor
import org.springframework.stereotype.Service

@Service
class CreateShippingUseCase(
    private val createShippingGateway: CreateShippingGateway,
) : CreateShippingInteractor {
    override fun execute(input: CreateShippingInteractor.Input): Shipping {
        val (uuid, name) = input

        return this.createShippingGateway.create(CreateShippingGateway.Input(uuid, name))
    }
}

package com.henriquebarucco.fsmshipping.domain.shipping.interactor

import com.henriquebarucco.fsmshipping.domain.shipping.entity.Shipping
import java.util.UUID

interface CreateShippingInteractor {
    fun execute(input: Input): Shipping

    data class Input(
        val uuid: UUID,
        val name: String,
    )
}

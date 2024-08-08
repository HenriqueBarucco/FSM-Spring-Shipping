package com.henriquebarucco.fsmshipping.domain.shipping.interactor

import com.henriquebarucco.fsmshipping.domain.shipping.entity.Shipping

interface CreateShippingInteractor {
    fun execute(input: Input): Shipping

    data class Input(
        val name: String,
    )
}

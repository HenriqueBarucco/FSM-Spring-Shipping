package com.henriquebarucco.fsmshipping.infra.entrypoint.http.dto.request

import java.util.UUID

data class CreateShippingRequest(
    val uuid: UUID,
    val name: String,
)

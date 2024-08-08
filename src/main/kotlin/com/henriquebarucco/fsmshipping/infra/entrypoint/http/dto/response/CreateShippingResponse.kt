package com.henriquebarucco.fsmshipping.infra.entrypoint.http.dto.response

import java.util.UUID

data class CreateShippingResponse(
    val id: UUID,
    val name: String,
    val state: String,
)

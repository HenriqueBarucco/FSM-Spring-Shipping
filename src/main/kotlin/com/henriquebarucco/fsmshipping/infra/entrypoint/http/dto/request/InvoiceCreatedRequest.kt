package com.henriquebarucco.fsmshipping.infra.entrypoint.http.dto.request

import java.util.UUID

data class InvoiceCreatedRequest(
    val invoice: UUID,
)

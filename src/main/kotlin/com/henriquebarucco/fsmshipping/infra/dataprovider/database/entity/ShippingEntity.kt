package com.henriquebarucco.fsmshipping.infra.dataprovider.database.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "shippings")
data class ShippingEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,
    val uuid: UUID,
    var name: String,
    @Enumerated(EnumType.STRING)
    var state: ShippingState = ShippingState.CREATED,
    var invoice: UUID? = null,
)

enum class ShippingState {
    CREATED,
    INVOICE_CREATED,
    SENT,
}

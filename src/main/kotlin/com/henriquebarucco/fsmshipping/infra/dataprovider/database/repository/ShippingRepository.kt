package com.henriquebarucco.fsmshipping.infra.dataprovider.database.repository

import com.henriquebarucco.fsmshipping.infra.dataprovider.database.entity.ShippingEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface ShippingRepository : JpaRepository<ShippingEntity, UUID> {
    fun findByUuid(uuid: UUID): Optional<ShippingEntity>
}

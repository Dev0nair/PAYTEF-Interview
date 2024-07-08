package com.ismaelgr.core.domain.entity

import com.ismaelgr.core.domain.utils.formatToString
import java.util.Calendar
import java.util.UUID

data class Case(
    val id: UUID,
    val ownerId: UUID,
    val material: String,
    val date: String,
    val type: String,
    val state: CaseState
)

fun Case_Mock(
    id: UUID = UUID.randomUUID(),
    ownerId: UUID = UUID.randomUUID(),
    material: String = "Material",
    date: String = Calendar.getInstance().formatToString(),
    type: String = "Type",
    state: CaseState = CaseState.entries.random()
) = Case(id, ownerId, material, date, type, state)

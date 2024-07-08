package com.ismaelgr.core.domain.entity

import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val userType: UserType
)

fun User_Mocked(
    id: UUID = UUID.randomUUID(),
    name: String = "Name",
    userType: UserType = UserType.entries.random()
) = User(id, name, userType)
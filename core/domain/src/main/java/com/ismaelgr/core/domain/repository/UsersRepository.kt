package com.ismaelgr.core.domain.repository

import com.ismaelgr.core.domain.entity.User
import java.util.UUID

interface UsersRepository {
    fun getUser(id: UUID): User?
    fun getAllUsers(): List<UUID>
    fun addUser(user: User)
    fun removeUser(id: UUID)
}
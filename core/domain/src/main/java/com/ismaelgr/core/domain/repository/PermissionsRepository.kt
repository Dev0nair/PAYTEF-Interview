package com.ismaelgr.core.domain.repository

import com.ismaelgr.core.domain.entity.Permission
import java.util.UUID

interface PermissionsRepository {
    fun getPermission(id: UUID): Permission?
    fun getAllPermission(): List<UUID>
    fun addPermission(permission: Permission)
    fun removePermission(id: UUID)
    
    fun getPermissionsOf(userId: UUID): List<Permission>
    fun getPermissionRelatedTo(caseId: UUID): List<Permission>
}

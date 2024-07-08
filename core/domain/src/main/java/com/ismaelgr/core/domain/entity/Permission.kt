package com.ismaelgr.core.domain.entity

import java.util.UUID

/**
 * @param fullAccessUserId in case there's a global permission, userId will contains the giving permissions to the fullAccessUserId cases. This field will be null when it's basic permission
 * @param caseId CaseId related to the basic permission. Will be null when it's global access
 * */
data class Permission(
    val id: UUID,
    val caseId: UUID?,
    val userId: UUID,
    val fullAccessUserId: UUID?,
    val permissionType: PermissionType,
    val permissionVisibilityType: PermissionVisibilityType
)

fun Permission_Mocked(
    id: UUID = UUID.randomUUID(),
    caseId: UUID? = null,
    userId: UUID = UUID.randomUUID(),
    fullAccessUserId: UUID? = null,
    permissionType: PermissionType = PermissionType.BASIC,
    permissionVisibilityType: PermissionVisibilityType = PermissionVisibilityType.READ_ONLY
) = Permission(id, caseId, userId, fullAccessUserId, permissionType, permissionVisibilityType)
package com.ismaelgr.core.domain.usecase

import com.ismaelgr.core.domain.entity.Case
import com.ismaelgr.core.domain.entity.Permission
import com.ismaelgr.core.domain.entity.PermissionType
import com.ismaelgr.core.domain.entity.PermissionVisibilityType
import com.ismaelgr.core.domain.repository.CaseRepository
import com.ismaelgr.core.domain.repository.PermissionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class GetLawerCasesUseCase @Inject constructor(private val caseRepository: CaseRepository, private val permissionsRepository: PermissionsRepository) {
    
    operator fun invoke(userId: UUID): Flow<List<Case>> = flow {
        // Our cases
        val ownerCases: List<Case> = caseRepository.getCasesOf(userId).mapNotNull { caseRepository.getCase(it) }
        val userPermissions: List<Permission> = permissionsRepository.getPermissionsOf(userId)
        val userPermissionsDenial: List<Permission> = userPermissions.filter { it.permissionVisibilityType == PermissionVisibilityType.INVISIBLE }
        val userPermissionsDenialIds: List<UUID> = userPermissionsDenial.map { it.id }
        // The cases we got basic access regardless of the permission (read_only or total_access).
        val basicPermissionsCases: List<Case> = userPermissions.filter { it.permissionType == PermissionType.BASIC && it.permissionVisibilityType != PermissionVisibilityType.INVISIBLE }.mapNotNull { it.caseId }.mapNotNull { permission -> caseRepository.getCase(permission) }
        // Now we get all the global permissions access, so we get all of them and for each fullAccessUserId (the lawer we'll get their cases) we'll get all of their cases and will filter of the individual cases permissions denials, in case we get all cases from one lawer except some of them
        val globalAccess: List<Permission> = userPermissions.filter { it.permissionType == PermissionType.GLOBAL }
        val externalCases: List<Case> = globalAccess.mapNotNull { it.fullAccessUserId }
            .flatMap { caseRepository.getCasesOf(it) } // Every case of all the users whom gave us permissions to their cases
            .filter { caseId -> caseId !in userPermissionsDenialIds } // Excluding the individual denials, in case a lawer wants us to get acces to all their cases except some of them
            .mapNotNull { caseRepository.getCase(it) } // Finally we get the Case instance
        val notRepeatedCases = (ownerCases + basicPermissionsCases + externalCases).distinctBy { it.id }.sortedByDescending { it.state.level }
        emit(notRepeatedCases)
    }
}
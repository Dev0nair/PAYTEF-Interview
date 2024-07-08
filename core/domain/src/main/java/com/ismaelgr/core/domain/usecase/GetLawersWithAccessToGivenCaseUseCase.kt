package com.ismaelgr.core.domain.usecase

import com.ismaelgr.core.domain.entity.Permission
import com.ismaelgr.core.domain.entity.PermissionType
import com.ismaelgr.core.domain.entity.PermissionVisibilityType
import com.ismaelgr.core.domain.entity.User
import com.ismaelgr.core.domain.repository.CaseRepository
import com.ismaelgr.core.domain.repository.PermissionsRepository
import com.ismaelgr.core.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class GetLawersWithAccessToGivenCaseUseCase @Inject constructor(private val caseRepository: CaseRepository, private val permissionsRepository: PermissionsRepository, private val usersRepository: UsersRepository) {
    
    /**
     * @return List of User and Permission associated with the Case. If Permission is null, it means the user is the owner
     * */
    operator fun invoke(caseId: UUID): Flow<List<Pair<User, Permission?>>> = flow {
        val ownerUser: User? = caseRepository.getCase(caseId)?.let { case -> usersRepository.getUser(case.ownerId) }
        val basicPermissionOfUser: List<Permission> = permissionsRepository.getPermissionRelatedTo(caseId)
        val basicPermissionGiven = basicPermissionOfUser.filter { it.permissionVisibilityType != PermissionVisibilityType.INVISIBLE }
        
        val basicPermissionDenialCases = basicPermissionOfUser.filter { it.permissionVisibilityType == PermissionVisibilityType.INVISIBLE }.map { it.userId }
        
        val ownerGlobalAccessGiven: List<Permission> = permissionsRepository.getAllPermission().mapNotNull { permissionId -> permissionsRepository.getPermission(permissionId) }
            .filter { permission ->
                permission.fullAccessUserId != null
                        && permission.fullAccessUserId == ownerUser?.id
                        && permission.permissionType == PermissionType.GLOBAL
            }
            .filter { permission -> permission.userId !in basicPermissionDenialCases }
        
        val pairedList: List<Pair<User, Permission?>> = (basicPermissionGiven + ownerGlobalAccessGiven)
            .distinctBy { it.id }
            .mapNotNull { permission ->
                val user = usersRepository.getUser(permission.userId) ?: return@mapNotNull null
                 user to permission
            }
            .let { actualList ->
                if(ownerUser != null) {
                    actualList + (ownerUser to null)
                } else {
                    actualList
                }
            }
        
        emit(pairedList)
    }
}
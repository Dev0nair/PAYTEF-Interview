package com.ismaelgr.core.data.repository

import com.ismaelgr.core.data.datasource.DataSource
import com.ismaelgr.core.domain.entity.Case
import com.ismaelgr.core.domain.entity.Permission
import com.ismaelgr.core.domain.entity.User
import com.ismaelgr.core.domain.repository.CaseRepository
import com.ismaelgr.core.domain.repository.PermissionsRepository
import com.ismaelgr.core.domain.repository.UsersRepository
import java.util.UUID
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataSource: DataSource
) : CaseRepository, UsersRepository, PermissionsRepository {
    
    override fun getCase(id: UUID): Case? = dataSource.getCase(id)
    
    override fun getAllCases(): List<UUID> = dataSource.getAllCases().map { it.id }
    
    override fun addCase(case: Case) = dataSource.addCase(case)
    
    override fun removeCase(id: UUID) = dataSource.removeCase(id)
    
    override fun getCasesOf(userId: UUID): List<UUID> {
        return dataSource.getAllCases().filter { case -> case.ownerId == userId }.map { it.id }
    }
    
    override fun getPermission(id: UUID): Permission? = dataSource.getPermission(id)
    
    override fun getAllPermission(): List<UUID> = dataSource.getAllPermission().map { it.id }
    
    override fun addPermission(permission: Permission) = dataSource.addPermission(permission)
    
    override fun removePermission(id: UUID) = dataSource.removePermission(id)
    
    override fun getPermissionsOf(userId: UUID): List<Permission> {
        return dataSource.getAllPermission().filter { it.userId == userId }
    }
    
    override fun getPermissionRelatedTo(caseId: UUID): List<Permission> {
        return dataSource.getAllPermission().filter { it.caseId == caseId }
    }
    
    override fun getUser(id: UUID): User? = dataSource.getUser(id)
    
    override fun getAllUsers(): List<UUID> = dataSource.getAllUsers().map { it.id }
    
    override fun addUser(user: User) = dataSource.addUser(user)
    
    override fun removeUser(id: UUID) = dataSource.removeUser(id)
}
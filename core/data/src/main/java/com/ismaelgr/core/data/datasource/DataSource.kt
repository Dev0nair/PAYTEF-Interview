package com.ismaelgr.core.data.datasource

import com.ismaelgr.core.domain.entity.Case
import com.ismaelgr.core.domain.entity.Permission
import com.ismaelgr.core.domain.entity.User
import com.ismaelgr.core.domain.entity.UserType
import java.util.UUID
import javax.inject.Inject

class DataSource @Inject constructor() {
    
    private val cases: MutableList<Case> = mutableListOf()
    private val permissions: MutableList<Permission> = mutableListOf()
    private val users: MutableList<User> = mutableListOf(
        User(id = UUID.randomUUID(), name = "Ismael", userType = UserType.LAWER)
    )
    
    fun getCase(id: UUID): Case? {
        return cases.find { it.id == id }
    }
    
    fun getAllCases(): List<Case> {
        return cases
    }
    
    fun addCase(case: Case) {
        cases.add(case)
    }
    
    fun removeCase(id: UUID) {
        cases.removeIf { it.id == id }
    }
    
    fun getUser(id: UUID): User? {
        return users.find { it.id == id }
    }
    
    fun getAllUsers(): List<User> {
        return users
    }
    
    fun addUser(user: User) {
        users.add(user)
    }
    
    fun removeUser(id: UUID) {
        users.removeIf { it.id == id }
    }
    
    fun getPermission(id: UUID): Permission? {
        return permissions.find { it.id == id }
    }
    
    fun getAllPermission(): List<Permission> {
        return permissions
    }
    
    fun addPermission(permission: Permission) {
        permissions.add(permission)
    }
    
    fun removePermission(id: UUID) {
        permissions.removeIf { it.id == id }
    }
}
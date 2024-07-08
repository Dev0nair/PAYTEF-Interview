package com.ismaelgr.core.domain.repository

import com.ismaelgr.core.domain.entity.Case
import java.util.UUID

interface CaseRepository {
    fun getCase(id: UUID): Case?
    fun getAllCases(): List<UUID>
    fun addCase(case: Case)
    fun removeCase(id: UUID)
    
    fun getCasesOf(userId: UUID): List<UUID>
}
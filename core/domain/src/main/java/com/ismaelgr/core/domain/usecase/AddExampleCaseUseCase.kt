package com.ismaelgr.core.domain.usecase

import com.ismaelgr.core.domain.entity.Case
import com.ismaelgr.core.domain.entity.CaseState
import com.ismaelgr.core.domain.entity.CaseType
import com.ismaelgr.core.domain.repository.CaseRepository
import com.ismaelgr.core.domain.utils.formatToString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

class AddExampleCaseUseCase @Inject constructor(private val caseRepository: CaseRepository) {
    
    operator fun invoke(userid: UUID): Flow<Unit> = flow {
        val currentCases = caseRepository.getAllCases()
        val newCase = Case(id = UUID.randomUUID(), material = "Material ${currentCases.size + 1}", ownerId = userid, date = Calendar.getInstance().formatToString(), type = CaseType.entries.random(), state = CaseState.entries.random())
        caseRepository.addCase(newCase)
    }
}
package com.ismaelgr.core.domain.usecase

import com.ismaelgr.core.domain.entity.Case
import com.ismaelgr.core.domain.entity.CaseState
import com.ismaelgr.core.domain.entity.CaseType
import com.ismaelgr.core.domain.repository.CaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class AddExcelDataUseCase @Inject constructor(private val caseRepository: CaseRepository) {
    
    operator fun invoke(userId: UUID): Flow<Unit> = flow {
        cleanCasesOfUser(userId)
        generateCases(userId)
    }
    
    private fun cleanCasesOfUser(userId: UUID) {
        caseRepository.getCasesOf(userId).forEach { caseId -> caseRepository.removeCase(caseId) }
    }
    
    private fun generateCases(userId: UUID) {
        createItem(CaseType.CIVIL, "2023-07-01", "Reclamación de daños y perjuicios", CaseState.ACTIVE, userId)
        createItem(CaseType.PENAL, "2023-06-20", "Robo agravado", CaseState.ACTIVE, userId)
        createItem(CaseType.ADMINISTRATIVO, "2023-05-15", "Impugnación de acto administrativo", CaseState.ACTIVE, userId)
        createItem(CaseType.LABORAL, "2023-04-10", "Despido injustificado", CaseState.ACTIVE, userId)
        createItem(CaseType.FAMILIAR, "2023-03-05", "Divorcio contencioso", CaseState.ACTIVE, userId)
        createItem(CaseType.CIVIL, "2024-02-25", "Sucesión", CaseState.ACTIVE, userId)
        createItem(CaseType.PENAL, "2024-02-10", "Lesiones graves", CaseState.ACTIVE, userId)
        createItem(CaseType.ADMINISTRATIVO, "2024-01-20", "Solicitud de licencia", CaseState.ACTIVE, userId)
        createItem(CaseType.LABORAL, "2024-01-05", "Reclamo de horas extras", CaseState.ACTIVE, userId)
        createItem(CaseType.FAMILIAR, "2023-12-25", "Custodia de menores", CaseState.ACTIVE, userId)
        createItem(CaseType.CIVIL, "2023-12-10", "Compraventa", CaseState.CANCELED, userId)
        createItem(CaseType.PENAL, "2023-11-20", "Estafa", CaseState.CANCELED, userId)
        createItem(CaseType.ADMINISTRATIVO, "2023-11-05", "Sanción administrativa", CaseState.CANCELED, userId)
        createItem(CaseType.LABORAL, "2023-10-25", "Renuncia", CaseState.CANCELED, userId)
        createItem(CaseType.FAMILIAR, "2023-10-10", "Adopción", CaseState.CLOSED, userId)
        createItem(CaseType.CIVIL, "2023-09-25", "Arrendamiento", CaseState.CLOSED, userId)
        createItem(CaseType.PENAL, "2023-09-10", "Falsificación de documentos", CaseState.CLOSED, userId)
        createItem(CaseType.ADMINISTRATIVO, "2023-08-20", "Otorgamiento de permiso", CaseState.CLOSED, userId)
        createItem(CaseType.LABORAL, "2023-08-05", "Contrato de trabajo", CaseState.CLOSED, userId)
        createItem(CaseType.FAMILIAR, "2023-07-25", "Pensión alimenticia", CaseState.CLOSED, userId)
    }
    
    private fun createItem(type: CaseType, date: String, material: String, caseState: CaseState, userId: UUID) {
        caseRepository.addCase(Case(id = UUID.randomUUID(), material = material, type = type, date = date, state = caseState, ownerId = userId))
    }
}




















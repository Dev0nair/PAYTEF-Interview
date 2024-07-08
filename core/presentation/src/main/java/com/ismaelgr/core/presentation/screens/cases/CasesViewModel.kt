package com.ismaelgr.core.presentation.screens.cases

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ismaelgr.core.domain.entity.Case
import com.ismaelgr.core.domain.entity.ErrorType
import com.ismaelgr.core.domain.usecase.AddExampleCaseUseCase
import com.ismaelgr.core.domain.usecase.GetLawerCasesUseCase
import com.ismaelgr.core.presentation.utils.flow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CasesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getLawerCasesUseCase: GetLawerCasesUseCase,
    private val addExampleCaseUseCase: AddExampleCaseUseCase
) : ViewModel() {
    
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state
    
    fun loadData() {
        val userIdParam: UUID = getParamUserId() ?: return
        
        getLawerCasesUseCase(userIdParam)
            .flow(scope = viewModelScope, onEach = { result ->
                _state.update { State.Data(result) }
            })
    }
    
    fun onCreateCaseClick() {
        val userIdParam: UUID = getParamUserId() ?: return
        addExampleCaseUseCase(userIdParam).flow(viewModelScope, onComplete = { loadData() })
    }
    
    private fun getParamUserId(): UUID? {
        val userIdParam: String? = savedStateHandle["userId"]
        if(userIdParam.isNullOrEmpty()) {
            _state.update { State.Error(ErrorType.NO_USER_FOUND) }
            return null
        } else {
            return UUID.fromString(userIdParam)
        }
    }
    
    sealed class State {
        data object Loading : State()
        data class Data(val data: List<Case>) : State()
        data class Error(val errorType: ErrorType) : State()
    }
}
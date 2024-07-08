package com.ismaelgr.core.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ismaelgr.core.domain.entity.ErrorType
import com.ismaelgr.core.domain.entity.User
import com.ismaelgr.core.domain.usecase.GetRandomUserUseCase
import com.ismaelgr.core.presentation.utils.flow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getRandomUserUseCase: GetRandomUserUseCase
) : ViewModel() {
    
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state
    
    fun loadRandomUser() {
        getRandomUserUseCase()
            .flow(scope = viewModelScope, onEach = { result ->
                when (result) {
                    is GetRandomUserUseCase.Result.Success -> {
                        _state.update { State.Data(result.user) }
                    }
                    
                    is GetRandomUserUseCase.Result.Error -> {
                        _state.update { State.Error(result.errorType) }
                    }
                }
            })
    }
    
    sealed class State {
        data object Loading : State()
        data class Data(val user: User) : State()
        data class Error(val errorType: ErrorType) : State()
    }
}
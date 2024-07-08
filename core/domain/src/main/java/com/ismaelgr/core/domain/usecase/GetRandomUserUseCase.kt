package com.ismaelgr.core.domain.usecase

import com.ismaelgr.core.domain.entity.ErrorType
import com.ismaelgr.core.domain.entity.User
import com.ismaelgr.core.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRandomUserUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    sealed class Result {
        data class Error(val errorType: ErrorType): Result()
        data class Success(val user: User): Result()
    }
    
    /**
     * Retrieve a random user or get NO_USER_FOUND
     * */
    operator fun invoke(): Flow<Result> = flow {
        val result: Result = usersRepository.getAllUsers().randomOrNull()
            .let { userId ->
                userId?.let { id -> usersRepository.getUser(id)?.let { user -> Result.Success(user) } }
                    ?: Result.Error(ErrorType.NO_USER_FOUND)
            }
        
        emit(result)
    }
}
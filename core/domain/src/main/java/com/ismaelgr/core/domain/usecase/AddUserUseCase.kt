package com.ismaelgr.core.domain.usecase

import com.ismaelgr.core.domain.repository.UsersRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(private val usersRepository: UsersRepository) {
}
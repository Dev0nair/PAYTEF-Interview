package com.ismaelgr.core.presentation.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.util.Locale

fun String.onlyFirstUpper() =
    this
        .lowercase(Locale.getDefault())
        .replaceFirstChar { it.uppercase() }


fun <T> Flow<T>.flow(scope: CoroutineScope, onEach: (T) -> Unit = {}, onComplete: () -> Unit = { }) =
    this
        .onEach { result ->
            withContext(Dispatchers.Main) {
                onEach(result)
            }
        }
        .onCompletion {
            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
        .flowOn(Dispatchers.IO)
        .launchIn(scope)
package com.richaa2.map.kmp.presentation.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableSharedFlow

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("No SnackbarHostState provided")
}

val LocalErrorFlow = compositionLocalOf<MutableSharedFlow<String>> {
    error("No ErrorFlow provided")
}
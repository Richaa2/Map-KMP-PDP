package com.richaa2.map.kmp.presentation.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.richaa2.map.kmp.presentation.utils.LocalErrorFlow
import com.richaa2.map.kmp.presentation.utils.LocalSnackbarHostState

@Composable
fun MapSnackBar(
    modifier: Modifier = Modifier,
) {
    val errorFlow = LocalErrorFlow.current
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        errorFlow.collect { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    Box(modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .navigationBarsPadding()
        )
    }
}
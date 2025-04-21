package com.richaa2.map.kmp.presentation.app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.richaa2.map.kmp.presentation.app.components.MapSnackBar
import com.richaa2.map.kmp.presentation.navigation.AppNavGraph
import com.richaa2.map.kmp.presentation.screens.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.utils.LocalErrorFlow
import com.richaa2.map.kmp.presentation.utils.LocalSnackbarHostState
import com.richaa2.mappdp.designsystem.theme.MapPDPTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    val appViewModel: AppViewModel = koinViewModel()

    val cameraPositionState = remember {
        CameraPositionState()
    }

    val snackbarHostState = remember { SnackbarHostState() }

    val errorFlow = remember { MutableSharedFlow<String>() }

    MapPDPTheme {
        CompositionLocalProvider(
            LocalErrorFlow provides errorFlow,
            LocalSnackbarHostState provides snackbarHostState
        ) {
            AppNavGraph(
                appViewModel = appViewModel,
                cameraPositionState = cameraPositionState,
            )
            MapSnackBar()
        }
    }
}



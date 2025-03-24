package com.richaa2.map.kmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.richaa2.map.kmp.presentation.navigation.AppNavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    //TODO ROUTING
    //TODO CUSTOM CLUSTER DESIGN (OPTIONAL)
    MaterialTheme {
        AppNavGraph()
    }
}

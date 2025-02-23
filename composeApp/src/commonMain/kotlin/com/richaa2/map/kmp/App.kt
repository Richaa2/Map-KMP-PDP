package com.richaa2.map.kmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.richaa2.map.kmp.presentation.navigation.AppNavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {

    // TODO VIEWMODELS
    // TODO USE CASES
    // TODO LOCATION PROVIDER
    MaterialTheme {
        AppNavGraph()
    }
}

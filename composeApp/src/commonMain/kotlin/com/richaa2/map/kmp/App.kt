package com.richaa2.map.kmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.richaa2.map.kmp.dependecies.AddLocationViewModel
import com.richaa2.map.kmp.dependecies.MapViewModel
import com.richaa2.map.kmp.dependecies.MyViewModel
import com.richaa2.mappdp.presentation.locationDetails.LocationDetailsViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.compose_multiplatform
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<MyViewModel>()
    val mapViewModel = koinViewModel<MapViewModel>()
    val secVM = koinViewModel<AddLocationViewModel>()
    val thrVM = koinViewModel<LocationDetailsViewModel>()

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Hello, ${viewModel.getHelloWorldString()}")
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}
package com.richaa2.map.kmp

import androidx.compose.ui.window.ComposeUIViewController
import com.richaa2.map.kmp.di.initKoin
import com.richaa2.map.kmp.presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}
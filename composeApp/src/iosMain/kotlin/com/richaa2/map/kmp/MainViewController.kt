package com.richaa2.map.kmp

import androidx.compose.ui.window.ComposeUIViewController
import com.richaa2.map.kmp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}
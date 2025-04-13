package com.richaa2.map.kmp.presentation

import androidx.compose.runtime.Composable
import com.richaa2.map.kmp.presentation.navigation.AppNavGraph
import com.richaa2.mappdp.designsystem.theme.MapPDPTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    //TODO DARK THEME FOR MAP
    //TODO CUSTOM CLUSTER DESIGN (OPTIONAL)
    //TODO PREVIEW
    //TODO ADD FONTS
    MapPDPTheme  {
        AppNavGraph()
    }
}



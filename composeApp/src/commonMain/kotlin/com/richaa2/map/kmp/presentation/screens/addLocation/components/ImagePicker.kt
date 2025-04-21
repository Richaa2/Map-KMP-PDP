package com.richaa2.map.kmp.presentation.screens.addLocation.components//package com.richaa2.mappdp.presentation.addLocation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap


@Composable
expect fun ImagePicker(
    selectedImageBitmap: ByteArray? = null,
    onImageSelected: (ByteArray?) -> Unit,
    onImageRemoved: () -> Unit,
)

expect fun ByteArray.toImageBitmap(): ImageBitmap
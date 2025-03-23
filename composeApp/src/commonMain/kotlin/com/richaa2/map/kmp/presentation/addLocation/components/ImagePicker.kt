package com.richaa2.map.kmp.presentation.addLocation.components//package com.richaa2.mappdp.presentation.addLocation.components

import androidx.compose.runtime.Composable
import org.jetbrains.skia.Data
import org.jetbrains.skia.Image
import androidx.compose.ui.graphics.ImageBitmap
import org.jetbrains.skia.Bitmap


@Composable
expect fun ImagePicker(
    selectedImageBitmap: ByteArray? = null,
    onImageSelected: (ByteArray?) -> Unit,
    onImageRemoved: () -> Unit,
)

expect fun ByteArray.toImageBitmap(): ImageBitmap
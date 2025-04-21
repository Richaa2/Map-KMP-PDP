package com.richaa2.map.kmp.presentation.screens.addLocation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.richaa2.map.kmp.presentation.screens.addLocation.getCurrentUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.tap_to_select_an_image
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject
import platform.posix.memcpy


@Composable
actual fun ImagePicker(
    selectedImageBitmap: ByteArray?,
    onImageSelected: (ByteArray?) -> Unit,
    onImageRemoved: () -> Unit,
) {

    val currentVC = getCurrentUIViewController()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clickable {
                currentVC?.let { vc ->
                    pickImage { data ->
                        onImageSelected(data)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (selectedImageBitmap != null) {

            Image(
                bitmap = selectedImageBitmap.toImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small)
            )

            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onImageRemoved() }
                    .align(Alignment.TopEnd)
                    .background(
                        MaterialTheme.colorScheme.surfaceContainerHighest,
                        MaterialTheme.shapes.small
                    ),
                tint = MaterialTheme.colorScheme.onSurface
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.surfaceContainerHighest,
                        shape = MaterialTheme.shapes.small
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.tap_to_select_an_image),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

fun pickImage(onImagePicked: (ByteArray?) -> Unit) {

    val picker = UIImagePickerController().apply {
        sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
    }


    val delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
        UINavigationControllerDelegateProtocol {
        override fun imagePickerController(
            picker: UIImagePickerController,
            didFinishPickingMediaWithInfo: Map<Any?, *>,
        ) {

            val image = UIImagePickerControllerOriginalImage?.let {
                didFinishPickingMediaWithInfo[it]
            } as? UIImage

            val imageData = image?.let { UIImageJPEGRepresentation(it, 0.8) }

            picker.dismissViewControllerAnimated(true, completion = null)
            onImagePicked(imageData?.toByteArray())
        }


        override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
            picker.dismissViewControllerAnimated(true, completion = null)
            onImagePicked(null)
        }
    }

    picker.delegate = delegate

    val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootVC?.presentViewController(picker, animated = true, completion = null)
}


@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    return ByteArray(length.toInt()).apply {
        usePinned { memcpy(it.addressOf(0), bytes(), length()) }
    }
}


actual fun ByteArray.toImageBitmap(): ImageBitmap =
    Image.makeFromEncoded(this).toComposeImageBitmap()
//package com.richaa2.mappdp.presentation.addLocation.components
//
//import android.graphics.Bitmap
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import coil.compose.rememberAsyncImagePainter
//import com.richaa2.mappdp.R
//import com.richaa2.mappdp.utils.uriToByteArray
//
//@Composable
//fun ImagePicker(
//    selectedImageBitmap: Bitmap?,
//    onImageSelected: (ByteArray?) -> Unit,
//    onImageRemoved: () -> Unit,
//) {
//
//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri ->
//        uri?.let {
//            val byteArrayImage = it.uriToByteArray(context)
//            onImageSelected(byteArrayImage)
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(400.dp)
//            .clickable { launcher.launch("image/*") },
//        contentAlignment = Alignment.Center
//    ) {
//        if (selectedImageBitmap != null) {
//            Image(
//                painter = rememberAsyncImagePainter(selectedImageBitmap),
//                contentDescription = stringResource(R.string.selected_image),
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clip(MaterialTheme.shapes.small)
//            )
//            Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(R.string.remove_image),
//                modifier = Modifier
//                    .padding(8.dp)
//                    .align(Alignment.TopEnd)
//                    .background(
//                        MaterialTheme.colorScheme.surfaceContainerHighest,
//                        MaterialTheme.shapes.small
//                    )
//                    .clickable { onImageRemoved() },
//                tint = MaterialTheme.colorScheme.onSurface
//            )
//        } else {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(
//                        MaterialTheme.colorScheme.surfaceContainerHighest,
//                        shape = MaterialTheme.shapes.small
//                    ),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = stringResource(R.string.tap_to_select_an_image),
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//            }
//        }
//    }
//}
//
//@Composable
//@Preview(showBackground = true)
//fun ImagePickerPreview() {
//    MaterialTheme {
//        ImagePicker(
//            selectedImageBitmap = null,
//            onImageSelected = {},
//            onImageRemoved = {}
//        )
//    }
//}
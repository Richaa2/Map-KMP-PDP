package com.richaa2.map.kmp.presentation.locationDetails.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
//                Text(stringResource(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
//                Text(stringResource(R.string.no))
            }
        }
    )
}
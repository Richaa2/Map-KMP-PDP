package com.richaa2.map.kmp.presentation.screens.locationDetails.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.no
import mapkmp.composeapp.generated.resources.yes
import org.jetbrains.compose.resources.stringResource

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
                Text(stringResource(Res.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(Res.string.no))
            }
        }
    )
}
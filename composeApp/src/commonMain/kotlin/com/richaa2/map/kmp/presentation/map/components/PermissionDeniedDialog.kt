package com.richaa2.map.kmp.presentation.map.components//package com.richaa2.mappdp.presentation.map.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun PermissionDeniedDialog(
    onDismiss: () -> Unit,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "stringResource(R.string.location_permission_required)")
        },
        text = {
            Text("stringResource(R.string.without_location_permission_your_current_location_will_not_be_visible_on_the_map)")
        },
        confirmButton = {
            TextButton(onClick = onRequestPermission) {
                Text("stringResource(R.string.grant_permission)")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("stringResource(R.string.dismiss)")
            }
        }
    )
}
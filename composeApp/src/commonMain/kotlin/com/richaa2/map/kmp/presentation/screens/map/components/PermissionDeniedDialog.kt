package com.richaa2.map.kmp.presentation.screens.map.components//package com.richaa2.mappdp.presentation.map.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.dismiss
import mapkmp.composeapp.generated.resources.grant_permission
import mapkmp.composeapp.generated.resources.location_permission_required
import mapkmp.composeapp.generated.resources.without_location_permission_your_current_location_will_not_be_visible_on_the_map
import org.jetbrains.compose.resources.stringResource

@Composable
fun PermissionDeniedDialog(
    onDismiss: () -> Unit,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(Res.string.location_permission_required))
        },
        text = {
            Text(stringResource(Res.string.without_location_permission_your_current_location_will_not_be_visible_on_the_map))
        },
        confirmButton = {
            TextButton(onClick = onRequestPermission) {
                Text(stringResource(Res.string.grant_permission))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.dismiss))
            }
        }
    )
}
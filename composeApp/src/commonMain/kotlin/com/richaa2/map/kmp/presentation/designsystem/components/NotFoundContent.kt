package com.richaa2.mappdp.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotFoundContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Icon(
//                imageVector = Icons.Default.Error,
//                contentDescription = stringResource(R.string.not_found),
//                tint = MaterialTheme.colorScheme.error,
//                modifier = Modifier.size(64.dp)
//            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "content not found",
//                text = stringResource(R.string.content_not_found),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun NotFoundContentPreview() {
//    MapPDPTheme {
//        NotFoundContent()
//    }
//}
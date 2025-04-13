package com.richaa2.map.kmp.core.error.impl

import com.richaa2.map.kmp.core.error.ErrorHandler
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.error_server
import org.jetbrains.compose.resources.getString


class DefaultErrorHandlerImpl   : ErrorHandler {
    override suspend fun getErrorMessage(throwable: Throwable?): String {
        return when (throwable) {
            null -> getString(Res.string.error_server)
            else -> throwable.message.toString()
        }

    }
}
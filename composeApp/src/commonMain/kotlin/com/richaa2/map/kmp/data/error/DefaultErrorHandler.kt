package com.richaa2.map.kmp.data.error

import com.richaa2.map.kmp.core.common.ErrorHandler


class DefaultErrorHandler  constructor(
//    private val resourceProvider: ResourceProvider,
) : ErrorHandler {
    override fun getErrorMessage(throwable: Throwable?): String {
        return when (throwable) {
            null -> "unknown error"
            else -> throwable.message.toString()
        }

    }
}
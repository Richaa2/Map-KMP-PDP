package com.richaa2.map.kmp.data.error

import com.richaa2.map.kmp.core.common.ErrorHandler


class DefaultErrorHandler  constructor(
//    private val resourceProvider: ResourceProvider,
) : ErrorHandler {
    override fun getErrorMessage(throwable: Throwable): String {
        return TODO()
//        return when (throwable) {
//            is HttpException -> resourceProvider.getString(
//                R.string.error_server,
//                throwable.message.toString()
//            )
//
//            is IOException -> resourceProvider.getString(R.string.error_network)
//            else -> resourceProvider.getString(R.string.error_unknown, throwable.message.toString())
//        }
    }
}
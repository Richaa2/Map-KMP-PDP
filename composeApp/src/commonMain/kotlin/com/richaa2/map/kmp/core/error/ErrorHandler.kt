package com.richaa2.map.kmp.core.error

fun interface ErrorHandler {
    suspend fun getErrorMessage(throwable: Throwable?): String
}
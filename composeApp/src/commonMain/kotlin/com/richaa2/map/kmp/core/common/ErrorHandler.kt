package com.richaa2.mappdp.core.common

fun interface ErrorHandler {
    fun getErrorMessage(throwable: Throwable): String
}
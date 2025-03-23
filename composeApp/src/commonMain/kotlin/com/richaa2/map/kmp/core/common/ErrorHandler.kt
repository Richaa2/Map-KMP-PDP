package com.richaa2.map.kmp.core.common

fun interface ErrorHandler {
    fun getErrorMessage(throwable: Throwable?): String
}
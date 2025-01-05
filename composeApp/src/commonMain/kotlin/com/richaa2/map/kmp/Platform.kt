package com.richaa2.map.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
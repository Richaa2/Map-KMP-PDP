package com.richaa2.map.kmp.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import platform.Foundation.NSBundle
import platform.Foundation.NSDictionary
import platform.Foundation.dictionaryWithContentsOfFile


actual val googleMapApiClient: HttpClient
    get() = HttpClient(Darwin) {
        install(HttpTimeout) {
            socketTimeoutMillis = 60_000
            requestTimeoutMillis = 60_000
        }
        defaultRequest {
            header("Content-Type", "application/json")
            header("X-Goog-Api-Key", getRoutesApiKey())
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    println("KtorClient: $message")
                }
            }
            level = LogLevel.ALL
        }
    }


fun getRoutesApiKey(): String {
    val path = NSBundle.mainBundle.pathForResource("Secrets", "plist")
        ?: error("Secrets.plist not found")
    val dict = NSDictionary.dictionaryWithContentsOfFile(path)

    return dict?.get("ROUTES_API_KEY") as? String
        ?: error("ROUTES_API_KEY not found in Secrets.plist")
}
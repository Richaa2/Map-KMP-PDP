package com.richaa2.map.kmp.data.source.remote.client

import android.util.Log
import com.richaa2.map.kmp.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual val ktorGoogleMapApiClient: HttpClient
    get() = HttpClient(OkHttp) {
        install(HttpTimeout) {
            socketTimeoutMillis = 60_000
            requestTimeoutMillis = 60_000
        }
        defaultRequest {
            header("Content-Type", "application/json")
            header("X-Goog-Api-Key", BuildConfig.ROUTES_API_KEY)
        }
        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    Log.i("KtorClient", message)
                }
            }
            level = LogLevel.ALL
        }


        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
    }
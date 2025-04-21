package com.richaa2.map.kmp.data.source.local.driver

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.richaa2.db.AppDatabase

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, LOCATION_DB_NAME)
    }

    companion object {
        const val LOCATION_DB_NAME = "locations_info.db"
    }
}
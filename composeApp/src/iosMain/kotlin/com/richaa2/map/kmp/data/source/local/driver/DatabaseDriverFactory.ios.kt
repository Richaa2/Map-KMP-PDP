package com.richaa2.map.kmp.data.source.local.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.richaa2.db.AppDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, LOCATION_DB_NAME)
    }

    companion object {
        const val LOCATION_DB_NAME = "locations_info.db"
    }
}
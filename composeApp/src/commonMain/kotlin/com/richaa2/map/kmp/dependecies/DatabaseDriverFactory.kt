package com.richaa2.map.kmp.dependecies

import app.cash.sqldelight.db.SqlDriver
import com.richaa2.db.AppDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): AppDatabase {
    val driver = driverFactory.createDriver()
    val database = AppDatabase(driver)
    return database
}
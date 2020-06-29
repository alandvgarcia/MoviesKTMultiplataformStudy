package com.alandvgarcia.kotlinmultiplataform.repository

import com.alandvgarcia.kotlinmultiplataform.db.MovieDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MovieDatabase.Schema, "movie.db")
    }
}
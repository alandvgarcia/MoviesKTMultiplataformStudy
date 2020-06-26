package com.alandvgarcia.kotlinmultiplataform.repository

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.alandvgarcia.db.MovieDatabase

internal actual fun cache(): MovieDatabase {
    val driver = NativeSqliteDriver(MovieDatabase.Schema, "movies.db")
    return MovieDatabase(driver)
}
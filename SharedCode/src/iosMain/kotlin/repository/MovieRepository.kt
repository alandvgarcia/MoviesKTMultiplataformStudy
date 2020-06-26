package com.alandvgarcia.kotlinmultiplataform.repository

import com.alandvgarcia.kotlinmultiplataform.db.MovieDatabase
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

internal actual fun database(): MovieDatabase {
    val driver = NativeSqliteDriver(MovieDatabase.Schema, "movie.db")
    return MovieDatabase(driver)
}
package com.alandvgarcia.kotlinmultiplataform.repository

import android.content.Context
import com.alandvgarcia.kotlinmultiplataform.db.MovieDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MovieDatabase.Schema, context, "movie.db")
    }
}

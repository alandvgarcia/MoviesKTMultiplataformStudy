package com.alandvgarcia.kotlinmultiplataform.repository

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.alandvgarcia.db.MovieDatabase

lateinit var appContext: Context

internal actual fun cache(): MovieDatabase {
    val driver = AndroidSqliteDriver(MovieDatabase.Schema, appContext, "movies.db")
    return MovieDatabase(driver)
}
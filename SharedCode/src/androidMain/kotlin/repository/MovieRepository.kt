package com.alandvgarcia.kotlinmultiplataform.repository

import android.content.Context
import com.alandvgarcia.kotlinmultiplataform.db.MovieDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver

lateinit var appContext: Context

internal actual fun database(): MovieDatabase {
    val driver = AndroidSqliteDriver(MovieDatabase.Schema, appContext, "movie.db")
    return MovieDatabase(driver)
}
package com.alandvgarcia.kotlinmultiplataform.repository

import com.alandvgarcia.kotlinmultiplataform.db.MovieDatabase
import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): MovieDatabase {
    val driver = driverFactory.createDriver()
    val database = MovieDatabase(driver)

    return database
}
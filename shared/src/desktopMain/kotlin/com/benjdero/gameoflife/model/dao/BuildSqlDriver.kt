package com.benjdero.gameoflife.model.dao

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

private const val FILENAME = "database.db"

fun buildSqlDriver(): SqlDriver {
    val file = File(FILENAME)
    val databaseExist: Boolean = file.isFile
    val driver = JdbcSqliteDriver(
        "jdbc:sqlite:$FILENAME"
    )
    if (!databaseExist)
        Database.Schema.create(driver)
    return driver
}
package com.benjdero.gameoflife.model.dao

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
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
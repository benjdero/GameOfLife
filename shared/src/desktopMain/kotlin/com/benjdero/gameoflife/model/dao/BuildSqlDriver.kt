package com.benjdero.gameoflife.model.dao

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

fun buildSqlDriver(): SqlDriver {
    val driver = JdbcSqliteDriver(
        "jdbc:sqlite:database.db"
    )
    Database.Schema.create(driver)
    return driver
}
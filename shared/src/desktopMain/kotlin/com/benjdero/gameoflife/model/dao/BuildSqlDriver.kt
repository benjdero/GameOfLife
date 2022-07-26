package com.benjdero.gameoflife.model.dao

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

fun buildSqlDriver(): SqlDriver {
    val driver = JdbcSqliteDriver(
        JdbcSqliteDriver.IN_MEMORY
    )
    Database.Schema.create(driver)
    return driver
}
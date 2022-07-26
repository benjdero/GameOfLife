package com.benjdero.gameoflife.model.dao

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

fun buildSqlDriver(): SqlDriver =
    NativeSqliteDriver(
        Database.Schema,
        "database.db"
    )
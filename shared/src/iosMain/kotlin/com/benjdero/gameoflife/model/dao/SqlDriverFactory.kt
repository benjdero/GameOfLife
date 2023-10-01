package com.benjdero.gameoflife.model.dao

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class SqlDriverFactory {
    actual fun create(): SqlDriver =
        NativeSqliteDriver(
            Database.Schema,
            "database.db"
        )
}

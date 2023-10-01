package com.benjdero.gameoflife.model.dao

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class SqlDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver =
        AndroidSqliteDriver(
            schema = Database.Schema,
            context = context,
            name = "database.db",
            callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    db.execSQL("PRAGMA foreign_key=ON;")
                }
            }
        )
}
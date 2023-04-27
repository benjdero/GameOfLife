package com.benjdero.gameoflife.model.dao

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

fun buildSqlDriver(context: Context): SqlDriver =
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
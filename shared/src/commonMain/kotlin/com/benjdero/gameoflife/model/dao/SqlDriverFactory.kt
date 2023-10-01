package com.benjdero.gameoflife.model.dao

import app.cash.sqldelight.db.SqlDriver

expect class SqlDriverFactory {
    fun create(): SqlDriver
}
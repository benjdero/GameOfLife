package com.benjdero.gameoflife.model.dao

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.logs.LogSqliteDriver
import com.benjdero.gameoflife.World as GolWorld

class DaoService(
    sqlDriver: SqlDriver
) {
    private val driver: SqlDriver = LogSqliteDriver(sqlDriver) { debugMessage: String ->
        println(debugMessage)
    }

    private val database: Database = Database(driver)

    fun insertWorld(world: GolWorld) {
        val byteArray: ByteArray = ByteArray(world.width * world.height) { index: Int ->
            if (world.cells[index]) 1 else 0
        }
        database.worldQueries.insert(world.width, world.height, byteArray)
    }

    fun findAllWorld(): List<GolWorld> =
        database.worldQueries.findAll { _: Long, width: Int, height: Int, cells: ByteArray ->
            val booleanArray: BooleanArray = BooleanArray(width * height) { index: Int ->
                cells[index].toInt() == 1
            }
            GolWorld(
                width = width,
                height = height,
                cells = booleanArray
            )
        }.executeAsList()
}
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

    fun saveWorld(world: GolWorld) {
        val byteArray = booleanArrayToByteArray(world.cells, world.width * world.height)
        when (world.saved) {
            GolWorld.Saved.Not ->
                database.worldQueries.insert(world.width, world.height, byteArray)
            is GolWorld.Saved.AsWorld ->
                database.worldQueries.update(world.width, world.height, byteArray, world.saved.id)
        }
    }

    fun deleteById(id: Long) {
        database.worldQueries.deleteById(id)
    }

    fun findAllWorld(): List<GolWorld> =
        database.worldQueries.findAll { id: Long, width: Int, height: Int, cells: ByteArray ->
            val booleanArray = byteArrayToBooleanArray(cells, width * height)
            GolWorld(
                saved = GolWorld.Saved.AsWorld(id),
                width = width,
                height = height,
                cells = booleanArray
            )
        }.executeAsList()

    /**
     * World cells are converted to ByteArray to be saved in the database
     * TODO: Could be hardly optimized
     */
    private fun booleanArrayToByteArray(array: BooleanArray, size: Int) =
        ByteArray(size) { index: Int ->
            if (array[index]) 1 else 0
        }

    private fun byteArrayToBooleanArray(array: ByteArray, size: Int) =
        BooleanArray(size) { index: Int ->
            array[index].toInt() == 1
        }
}
package com.benjdero.gameoflife.model

fun mockWorld(): World =
    World(
        saved = World.Saved.Not,
        width = 4,
        height = 3,
        cells = booleanArrayOf(
            true, true, false, true,
            false, true, false, false,
            true, false, true, false
        )
    )

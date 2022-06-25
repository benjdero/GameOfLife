package com.benjdero.gameoflife

actual class Platform actual constructor() {
    actual val platform: String = System.getProperty("os.name")
}
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "GameOfLife"
include(":androidApp")
include(":desktopApp")
include(":shared")
include(":sharedUi")

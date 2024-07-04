import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions.jvmTarget = "18"
        }
    }

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "18"
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget: KotlinNativeTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            export(libs.mvikotlin)
            export(libs.mvikotlinLogging)
            export(libs.mvikotlinTimetravel)
            export(libs.decompose)
            export(libs.essenty)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines)
            api(libs.mvikotlin)
            api(libs.mvikotlinLogging)
            api(libs.mvikotlinTimetravel)
            implementation(libs.mvikotlinCoroutines)
            api(libs.decompose)
            api(libs.essenty)
            implementation(libs.sqldelightRuntime)
            implementation(libs.sqldelightPrimitive)
            implementation(compose.runtime)
            api(compose.components.resources)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            implementation(libs.sqldelightAndroidDriver)
        }
        jvmMain.dependencies {
            implementation(libs.coroutinesDesktop)
            implementation(libs.sqldelightDesktopDriver)
        }
        iosMain.dependencies {
            implementation(libs.sqldelightNativeDriver)
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.benjdero.gameoflife"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    kotlin {
        jvmToolchain(18)
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.benjdero.gameoflife.model.dao")
            srcDirs("src/commonMain/sql")
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.benjdero.gameoflife.resources"
    generateResClass = always
}

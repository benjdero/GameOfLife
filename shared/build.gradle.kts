import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    id("app.cash.sqldelight")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    androidTarget()
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
            baseName = "shared"
            export(libs.mvikotlin)
            export(libs.mvikotlinLogging)
            export(libs.mvikotlinTimetravel)
            export(libs.decompose)
            export(libs.essenty)
            export(libs.mokoResources)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines)
            api(libs.mvikotlin)
            api(libs.mvikotlinLogging)
            api(libs.mvikotlinTimetravel)
            implementation(libs.mvikotlinRx)
            implementation(libs.mvikotlinCoroutines)
            api(libs.decompose)
            api(libs.essenty)
            implementation(libs.sqldelightRuntime)
            implementation(libs.sqldelightPrimitive)
            api(libs.mokoResources)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.mokoResourcesTest)
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
    sourceSets["main"].java.srcDirs("build/generated/moko/androidMain/src")

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

multiplatformResources {
    multiplatformResourcesPackage = "com.benjdero.gameoflife"
    multiplatformResourcesClassName = "Res"
}
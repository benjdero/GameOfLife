import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.mokoResources)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
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
            export(libs.mokoResources)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
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
    resourcesPackage.set("com.benjdero.gameoflife")
    resourcesClassName.set("Res")
}

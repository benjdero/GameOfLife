import org.jetbrains.kotlin.gradle.dsl.JvmTarget
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
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget: KotlinNativeTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            export(libs.mvikotlin.core)
            export(libs.mvikotlin.logging)
            export(libs.mvikotlin.timetravel)
            export(libs.decompose.core)
            export(libs.essenty)
            export(libs.mokoResources.core)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
            api(libs.mvikotlin.core)
            api(libs.mvikotlin.logging)
            api(libs.mvikotlin.timetravel)
            implementation(libs.mvikotlin.coroutines)
            api(libs.decompose.core)
            api(libs.essenty)
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.primitive)
            api(libs.mokoResources.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.mokoResources.test)
        }
        androidMain.dependencies {
            implementation(libs.sqldelight.android)
        }
        jvmMain.dependencies {
            implementation(libs.coroutines.desktop)
            implementation(libs.sqldelight.desktop)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native)
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        jvmToolchain(21)
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

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
    jvm("desktop") {
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
        val commonMain by getting {
            dependencies {
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
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.mokoResourcesTest)
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.sqldelightAndroidDriver)
            }
        }
        val androidUnitTest by getting
        val desktopMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.coroutinesDesktop)
                implementation(libs.sqldelightDesktopDriver)
            }
        }
        val desktopTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.sqldelightNativeDriver)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
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

multiplatformResources {
    multiplatformResourcesPackage = "com.benjdero.gameoflife"
    multiplatformResourcesClassName = "Res"
}
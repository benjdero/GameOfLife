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
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            export("com.arkivanov.mvikotlin:mvikotlin:${libs.versions.mvikotlin.get()}")
            export("com.arkivanov.mvikotlin:mvikotlin-logging:${libs.versions.mvikotlin.get()}")
            export("com.arkivanov.mvikotlin:mvikotlin-timetravel:${libs.versions.mvikotlin.get()}")
            export("com.arkivanov.decompose:decompose:${libs.versions.decompose.get()}")
            export("com.arkivanov.essenty:lifecycle:${libs.versions.essenty.get()}")
            export("dev.icerock.moko:resources:${libs.versions.mokoResources.get()}")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${libs.versions.coroutines.get()}")
                api("com.arkivanov.mvikotlin:mvikotlin:${libs.versions.mvikotlin.get()}")
                api("com.arkivanov.mvikotlin:mvikotlin-logging:${libs.versions.mvikotlin.get()}")
                api("com.arkivanov.mvikotlin:mvikotlin-timetravel:${libs.versions.mvikotlin.get()}")
                implementation("com.arkivanov.mvikotlin:rx:${libs.versions.mvikotlin.get()}")
                implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${libs.versions.mvikotlin.get()}")
                api("com.arkivanov.decompose:decompose:${libs.versions.decompose.get()}")
                api("com.arkivanov.essenty:lifecycle:${libs.versions.essenty.get()}")
                implementation("app.cash.sqldelight:runtime:${libs.versions.sqldelight.get()}")
                implementation("app.cash.sqldelight:primitive-adapters:${libs.versions.sqldelight.get()}")
                api("dev.icerock.moko:resources:${libs.versions.mokoResources.get()}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("dev.icerock.moko:resources-test:${libs.versions.mokoResources.get()}")
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("app.cash.sqldelight:android-driver:${libs.versions.sqldelight.get()}")
            }
        }
        val androidUnitTest by getting
        val desktopMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${libs.versions.coroutines.get()}")
                implementation("app.cash.sqldelight:sqlite-driver:${libs.versions.sqldelight.get()}")
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
                implementation("app.cash.sqldelight:native-driver:${libs.versions.sqldelight.get()}")
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
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    namespace = "com.benjdero.gameoflife"
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
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
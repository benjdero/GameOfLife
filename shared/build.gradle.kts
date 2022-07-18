plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
}

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            export("com.arkivanov.mvikotlin:mvikotlin:${Version.mvikotlin}")
            export("com.arkivanov.mvikotlin:mvikotlin-logging:${Version.mvikotlin}")
            export("com.arkivanov.mvikotlin:mvikotlin-timetravel:${Version.mvikotlin}")
            export("com.arkivanov.decompose:decompose:${Version.decompose}")
            export("com.arkivanov.essenty:lifecycle:${Version.essenty}")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}")
                api("com.arkivanov.mvikotlin:mvikotlin:${Version.mvikotlin}")
                api("com.arkivanov.mvikotlin:mvikotlin-logging:${Version.mvikotlin}")
                api("com.arkivanov.mvikotlin:mvikotlin-timetravel:${Version.mvikotlin}")
                implementation("com.arkivanov.mvikotlin:rx:${Version.mvikotlin}")
                implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${Version.mvikotlin}")
                api("com.arkivanov.decompose:decompose:${Version.decompose}")
                api("com.arkivanov.essenty:lifecycle:${Version.essenty}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val desktopMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Version.coroutines}")
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
    compileSdk = Version.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
    }
}
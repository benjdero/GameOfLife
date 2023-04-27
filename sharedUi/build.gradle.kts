plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Version.compose
    id("com.android.library")
}

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "18"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.animation)
                implementation(compose.uiTooling)
                implementation(compose.materialIconsExtended)
                api("com.arkivanov.decompose:extensions-compose-jetbrains:${Version.decompose}")
                implementation("dev.icerock.moko:resources-compose:${Version.mokoResources}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val desktopMain by getting
        val desktopTest by getting
    }
}

android {
    compileSdk = Version.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    namespace = "com.benjdero.gameoflife.sharedui"
    defaultConfig {
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}

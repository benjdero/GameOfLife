plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version libs.versions.compose.get()
    id("com.android.library")
}

kotlin {
    androidTarget()
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "18"
        }
    }

    sourceSets {
        commonMain.dependencies {
                implementation(project(":shared"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.animation)
                implementation(compose.uiTooling)
                implementation(compose.materialIconsExtended)
                api(libs.decomposeCompose)
                implementation(libs.mokoResourcesCompose)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.benjdero.gameoflife.sharedui"

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

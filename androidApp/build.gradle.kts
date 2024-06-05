plugins {
    alias(libs.plugins.androidApplication)
    kotlin("android")
    alias(libs.plugins.composeCompiler)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.benjdero.gameoflife.android"

    defaultConfig {
        applicationId = "com.benjdero.gameoflife.android"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    kotlinOptions {
        jvmTarget = "18"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.shared)
    implementation(projects.sharedUi)
    implementation(libs.activityCompose)
    implementation(libs.appcompat)
    implementation(libs.material)
}
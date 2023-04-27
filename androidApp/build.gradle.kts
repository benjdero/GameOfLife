plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = Version.compileSdk
    namespace = "com.benjdero.gameoflife.android"

    defaultConfig {
        applicationId = "com.benjdero.gameoflife.android"
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
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

    composeOptions {
        kotlinCompilerExtensionVersion = Version.composeCompiler
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":sharedUi"))
    implementation("androidx.activity:activity-compose:${Version.activityCompose}")
    implementation("androidx.appcompat:appcompat:${Version.appcompat}")
    implementation("com.google.android.material:material:${Version.material}")
}
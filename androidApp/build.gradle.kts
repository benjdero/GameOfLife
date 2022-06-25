plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = Version.compileSdk

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:${Version.material}")
    implementation("androidx.appcompat:appcompat:${Version.appcompat}")
    implementation("androidx.constraintlayout:constraintlayout:${Version.constraintlayout}")
}
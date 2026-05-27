import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.mokoResources)
    alias(libs.plugins.serialization)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            export(libs.mvikotlin.core)
            export(libs.mvikotlin.logging)
            export(libs.mvikotlin.timetravel)
            export(libs.decompose.core)
            export(libs.essenty)
            export(libs.mokoResources.core)
        }
    }

    jvm()

    androidLibrary {
        namespace = "com.benjdero.gameoflife.Shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

//        sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//        sourceSets["main"].java.srcDirs("build/generated/moko/androidMain/src")

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutinesCore)
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
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.sqldelight.android)
        }
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.sqldelight.desktop)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native)
        }
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

import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version libs.versions.compose.get()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "18"
        }
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(project(":sharedUi"))
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "jvm"
            packageVersion = "1.0.0"
            macOS {
                iconFile.set(project.file("src/jvmMain/resources/drawable/icon.icns"))
            }
            windows {
                iconFile.set(project.file("src/jvmMain/resources/drawable/icon.ico"))
            }
            linux {
                iconFile.set(project.file("src/jvmMain/resources/drawable/icon.png"))
            }
        }
    }
}
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    jvmToolchain(18)
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.shared)
                implementation(projects.sharedUi)
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
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(projects.sharedUI)

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)
}

compose.desktop {
    application {
        mainClass = "com.benjdero.gameoflife.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.benjdero.gameoflife"
            packageVersion = "1.0.0"
            macOS {
                iconFile.set(project.file("src/main/resources/drawable/icon.icns"))
            }
            windows {
                iconFile.set(project.file("src/main/resources/drawable/icon.ico"))
            }
            linux {
                iconFile.set(project.file("src/main/resources/drawable/icon.png"))
            }
        }
    }
}
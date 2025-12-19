import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // 1. Koin Core и Compose
            implementation("io.insert-koin:koin-core:4.0.0")
            implementation("io.insert-koin:koin-compose:4.0.0")

            // 2. Koin для ViewModel (специальная либа для KMP)
            implementation("io.insert-koin:koin-compose-viewmodel:4.0.0")

            // 3. Базовая библиотека ViewModel от Jetbrains
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}



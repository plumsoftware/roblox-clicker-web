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

            // Kotlin coroutines
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.10.2")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-web:1.10.2")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

// Регистрируем новую задачу "packageGame"
tasks.register<Zip>("packageGame") {
    // Описание, чтобы понимать, что это (видно в IDE)
    group = "distribution"
    description = "Собирает игру в ZIP архив для Яндекс Игр (без лишних папок)"

    // 1. Сначала говорим Gradle, что перед упаковкой нужно собрать проект
    // Используем 'jsBrowserDistribution' для JS (или 'wasmJsBrowserDistribution', если ты на Wasm)
    dependsOn("jsBrowserDistribution")

    // 2. Откуда брать файлы?
    // Берем из папки, куда Gradle складывает готовую Production версию
    from(layout.buildDirectory.dir("dist/js/productionExecutable"))

    // 3. Исключаем лишнее (файлы отладки .map весят много и не нужны игроку)
    exclude("**/*.map")

    // 4. Куда положить готовый архив?
    // Положим прямо в папку build/
    destinationDirectory.set(layout.buildDirectory)

    // 5. Как назвать файл?
    archiveFileName.set("game-release.zip")
}



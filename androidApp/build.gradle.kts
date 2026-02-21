plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "my.sdl.smarthome.remoteapp.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "my.sdl.smarthome.remoteapp.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
        }
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.koin.android)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.runtime)
    // Only Android integration
    implementation("androidx.activity:activity-compose:1.8.0")
//    implementation("androidx.compose.ui:ui:1.5.0")
//
//    // Material3 components
//    implementation("androidx.compose.material3:material3:1.2.0")
//
//    // Integration with Activity
//    implementation("androidx.activity:activity-compose:1.8.0")
//
//    // Optional for dev: previews and debugging
//    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")
//    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
}
plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "co.touchlab.kampkit.android"
    compileSdk = kmpLibs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "co.touchlab.kampkit"
        minSdk = kmpLibs.versions.minSdk.get().toInt()
        targetSdk = kmpLibs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    lint {
        warningsAsErrors = false
        abortOnError = true
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources.excludes.add("META-INF/**/previous-compilation-data.bin")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

val GROUP:String by project
val LIBRARY_VERSION:String by project

dependencies {
    implementation(project(":analytics"))
    implementation(project(":breeds"))
    implementation(libs.bundles.app.ui)
    implementation(libs.koin.android)
    coreLibraryDesugaring(libs.android.desugaring)
}

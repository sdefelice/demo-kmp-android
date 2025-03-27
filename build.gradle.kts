plugins {
    kotlin("multiplatform") version libs.versions.kotlin.get() apply false
    kotlin("plugin.serialization") version libs.versions.kotlin.get() apply false
    id("com.android.library") version kmpLibs.versions.android.gradle.plugin.get() apply false
    alias(libs.plugins.compose.compiler) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    val GROUP: String by project
    val VERSION_NAME: String by project
    group = GROUP
    version = VERSION_NAME
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

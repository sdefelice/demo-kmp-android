pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

include(":app")

/*
dependencyResolutionManagement {
    versionCatalogs {
        create("kmpLibs") {
            from(files("library/gradle/libs.versions.toml"))
        }
    }
}

include(":app", ":breeds", ":analytics")
project(":breeds").projectDir = File("library/breeds")
project(":analytics").projectDir = File("library/analytics")
*/


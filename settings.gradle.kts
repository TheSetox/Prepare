import java.io.FileInputStream
import java.util.Properties

rootProject.name = "Prepare"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/TheSetox/Prepare")
            val prop = Properties().apply {
                load(FileInputStream(File(rootProject.projectDir, "local.properties")))
            }
            val user = prop.getProperty("gpr.user")
            val token = prop.getProperty("gpr.key")
            credentials {
                username = user ?: System.getenv("USERNAME")
                password = token ?: System.getenv("TOKEN")
            }
        }
    }
}

include(":sample")
include(":android")
include(":multiplatform")

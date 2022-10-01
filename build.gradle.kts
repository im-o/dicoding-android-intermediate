// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.gradle_plugin apply false
    id("com.android.library") version Versions.gradle_plugin apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlin_gradle_plugin apply false
    id("com.google.dagger.hilt.android") version Versions.hilt_android apply false
}

tasks.register(name = "type", type = Delete::class) {
    delete(rootProject.buildDir)
}
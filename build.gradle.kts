// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.gradle_plugin apply false
    id("com.android.library") version Versions.gradle_plugin apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlin_gradle_plugin apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version Versions.gradle_maps_platform apply false
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_android}")
    }
}

tasks.register(name = "type", type = Delete::class) {
    delete(rootProject.buildDir)
}
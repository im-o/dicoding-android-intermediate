import dependencies.MyDependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdk = Versions.compile_sdk

    defaultConfig {
        applicationId = "com.rivaldy.id.dicoding"
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
        versionCode = Versions.version_code
        versionName = Versions.version_name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(Modules.commons))
    implementation(project(Modules.core))

    // DEFAULT DEPENDENCIES
    testImplementation(MyDependencies.junit)
    androidTestImplementation(MyDependencies.test_ext_junit)
    androidTestImplementation(MyDependencies.espresso_core)

    // Hilt
    implementation(MyDependencies.hilt_android)
    kapt(MyDependencies.hilt_android_compiler)

    // Splash Screen
    implementation(MyDependencies.splash_screen)

    // Glide
    annotationProcessor(MyDependencies.glide_compiler)
}
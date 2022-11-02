import dependencies.MyDependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    compileSdk = Versions.compile_sdk

    defaultConfig {
        applicationId = "com.rivaldy.id.dicoding"
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
        versionCode = Versions.version_code
        versionName = Versions.version_name

        testInstrumentationRunner = "com.rivaldy.id.dicoding.util.HiltTestRunner"
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
    testOptions {
        animationsDisabled = true
    }
}

dependencies {
    implementation(project(Modules.commons))
    implementation(project(Modules.core))

    // Maps SDK for Android
    implementation(MyDependencies.google_maps)

    // DEFAULT DEPENDENCIES
    testImplementation(MyDependencies.junit)
    androidTestImplementation(MyDependencies.junit)
    androidTestImplementation(MyDependencies.test_ext_junit)

    // Hilt
    implementation(MyDependencies.hilt_android)
    kapt(MyDependencies.hilt_android_compiler)

    // Splash Screen
    implementation(MyDependencies.splash_screen)

    // Glide
    kapt(MyDependencies.glide_compiler)

    // MOCKITO
    testImplementation(MyDependencies.mockito)
    testImplementation(MyDependencies.mockito_inline)

    // Special testing
    androidTestImplementation(MyDependencies.core_testing) //InstantTaskExecutorRule
    androidTestImplementation(MyDependencies.coroutines_test) //TestDispatcher
    testImplementation(MyDependencies.core_testing) // InstantTaskExecutorRule
    testImplementation(MyDependencies.coroutines_test) //TestDispatcher

    // Hilt for testing
    testImplementation(MyDependencies.hilt_android_testing) // For Robolectric tests.
    kaptTest(MyDependencies.hilt_android_compiler)
    androidTestImplementation(MyDependencies.hilt_android_testing) // For instrumented tests.
    kaptAndroidTest(MyDependencies.hilt_android_compiler)

    // Espresso
    androidTestImplementation(MyDependencies.espresso_core)
    androidTestImplementation(MyDependencies.espresso_contrib)
    androidTestImplementation(MyDependencies.espresso_intents)
    androidTestImplementation(MyDependencies.espresso_idling_resource)

    // Mock web server
    androidTestImplementation(MyDependencies.mock_web_server)
    androidTestImplementation(MyDependencies.mock_web_server_okhttp3)
}
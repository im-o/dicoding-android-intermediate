import dependencies.MyDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdk = Versions.compile_sdk

    defaultConfig {
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev\"")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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
    kotlinOptions {
        val commonCompilerArgs = listOf<String>()
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = commonCompilerArgs + listOf("-opt-in=kotlin.RequiresOptIn")
    }
    packagingOptions {
        resources {
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/NOTICE.txt"
        }
    }

    namespace = "com.rivaldy.id.core"
}

dependencies {
    // DEFAULT DEPENDENCIES
    api(MyDependencies.core_ktx)
    api(MyDependencies.appcompat)
    api(MyDependencies.material)
    testImplementation(MyDependencies.junit)
    androidTestImplementation(MyDependencies.test_ext_junit)
    androidTestImplementation(MyDependencies.espresso_core)

    // REMOTE
    api(MyDependencies.retrofit)
    api(MyDependencies.retrofit2_converter_gson)
    api(MyDependencies.retrofit2_adapter_rxjava2)
    api(MyDependencies.okhttp3)

    // Hilt
    implementation(MyDependencies.hilt_android)
    kapt(MyDependencies.hilt_android_compiler)
    api(MyDependencies.hilt_navigation) {
        exclude("androidx.lifecycle", "lifecycle-viewmodel-ktx")
    }

    // Room
    api(MyDependencies.room)
    kapt(MyDependencies.room_kapt)
    api(MyDependencies.room_ktx)

    // LIFECYCLE VIEWMODEL
    api(MyDependencies.lifecycle_viewmodel)

    // FACEBOOK SHIMMER
    api(MyDependencies.facebook_shimmer)

    // GLIDE
    api(MyDependencies.glide)
    kapt(MyDependencies.glide_compiler)
    api(MyDependencies.swipe_refresh_layout)

    // CAMERA X
    api(MyDependencies.camerax_camera2)
    api(MyDependencies.camerax_lifecycle)
    api(MyDependencies.camerax_view)

    // DESUGAR
    coreLibraryDesugaring(MyDependencies.desugar)

    // PAGING
    api(MyDependencies.paging_runtime)
    api(MyDependencies.room_paging)

    // MOCKITO
    testImplementation(MyDependencies.mockito)
    testImplementation(MyDependencies.mockito_inline)

    //special testing
    androidTestImplementation(MyDependencies.core_testing) //InstantTaskExecutorRule
    androidTestImplementation(MyDependencies.coroutines_test) //TestDispatcher
    testImplementation(MyDependencies.core_testing) // InstantTaskExecutorRule
    testImplementation(MyDependencies.coroutines_test) //TestDispatcher
}
package dependencies

/** Created by github.com/im-o on 10/1/2022. */

object MyDependencies {
    // DEFAULT DEPENDENCIES
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx_version}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.app_compat_version}"
    const val material = "com.google.android.material:material:${Versions.material_version}"
    const val junit = "junit:junit:${Versions.junit_version}"
    const val test_ext_junit = "androidx.test.ext:junit:${Versions.test_ext_junit_version}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_test_version}"

    // REMOTE
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    const val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"
    const val retrofit2_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit_version}"
    const val okhttp3 = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp3_version}"

    //Hilt
    const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt_android}"
    const val hilt_android_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt_android}"
    const val hilt_navigation = "androidx.hilt:hilt-navigation-fragment:${Versions.hilt_navigation}"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_ktx_version}"

    //Room
    const val room = "androidx.room:room-runtime:${Versions.room_version}"
    const val room_kapt = "androidx.room:room-compiler:${Versions.room_version}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room_version}" // optional - Kotlin Extensions and Coroutines support for Room

    //Splash Screen
    const val splash_screen = "androidx.core:core-splashscreen:${Versions.splash_screen_version}"

    //Facebook Shimmer
    const val facebook_shimmer = "com.facebook.shimmer:shimmer:${Versions.facebook_shimmer_version}"

    //Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"

    // Swipe Refresh Layout
    const val swipe_refresh_layout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_version}"

    // CameraX
    const val camerax_camera2 = "androidx.camera:camera-camera2:${Versions.camerax_version}"
    const val camerax_view = "androidx.camera:camera-view:${Versions.camerax_version}"
    const val camerax_lifecycle = "androidx.camera:camera-lifecycle:${Versions.camerax_version}"

    // Google Maps
    const val google_maps = "com.google.android.gms:play-services-maps:${Versions.google_maps_version}"
    const val google_maps_location = "com.google.android.gms:play-services-location:${Versions.google_maps_location_version}"

    // Desugar
    const val desugar = "com.android.tools:desugar_jdk_libs:${Versions.desugar_version}"

    // Paging 3
    const val paging_runtime = "androidx.paging:paging-runtime-ktx:${Versions.paging_version}"
    const val room_paging = "androidx.room:room-paging:${Versions.room_paging_version}"

    // Mockito
    const val mockito = "org.mockito:mockito-core:${Versions.mockito_version}"
    const val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito_version}"

    // Special Testing
    const val core_testing = "androidx.arch.core:core-testing:${Versions.core_testing_version}"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines_test_version}"

    // Hilt Android Testing
    const val hilt_android_testing = "com.google.dagger:hilt-android-testing:${Versions.hilt_android}"

    // Espresso
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso_test_version}"
    const val espresso_intents = "androidx.test.espresso:espresso-intents:${Versions.espresso_test_version}"
    const val espresso_idling_resource = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso_test_version}"

    // MockWebServer
    const val mock_web_server = "com.squareup.okhttp3:mockwebserver:${Versions.mock_web_server_version}"
    const val mock_web_server_okhttp3 = "com.squareup.okhttp3:okhttp-tls:${Versions.mock_web_server_version}"

}
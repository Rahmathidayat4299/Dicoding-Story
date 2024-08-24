plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.roomKsp)
}

android {
    namespace = "com.course.dicodingstory"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.course.dicodingstory"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    flavorDimensions += "env"
    productFlavors {
        create("production") {
            buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")
        }
        create("integration") {
            buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // retrofit & okhttp
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    //coil image
    implementation(libs.coil.compose)

    // unit testing
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.turbine)
    implementation(libs.coil)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx.v262)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // chucker
    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)
    // koin
    implementation(libs.koin.android)
    implementation (libs.koin.androidx.compose)
    // rule viewmodel
    testImplementation(libs.androidx.core.testing)
    //compose navigation
    implementation (libs.androidx.navigation.compose)
    //lat and lon to show location
    implementation (libs.play.services.location)
    //paging 3
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    //datetime
    implementation (libs.jetbrains.kotlinx.datetime)
    // CameraX
    val cameraxVersion = "1.3.0-rc01"

    implementation("androidx.camera:camera-core:$cameraxVersion")
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-video:$cameraxVersion")

    implementation("androidx.camera:camera-view:$cameraxVersion")
    implementation("androidx.camera:camera-extensions:$cameraxVersion")

    // Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.31.3-beta")

    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

}
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version "1.9.0"
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.anddevcorp.composenavigation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.anddevcorp.composenavigation"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "API_KEY", localProperties.getProperty("apiKey"))
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.adaptive.layout.android)
    implementation(libs.androidx.material3.adaptive.navigation.suite.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation ("androidx.compose.material3:material3:1.2.1")
    implementation ("androidx.compose.foundation:foundation:1.6.8")
    implementation ("androidx.compose.runtime:runtime:1.6.8")

//    implementation ("androidx.navigation:navigation-compose:2.7.7")

    implementation(libs.kotlinx.serialization.json)


    implementation(libs.androidx.navigation.compose)
    implementation(libs.squareup.retrofit)
    implementation(libs.converter.gson)
// Retrofit ve ScalarsConverter bağımlılıkları
    implementation(libs.squareup.retrofit)
    implementation(libs.converter.scalars)
// Hilt için Retrofit entegrasyonu
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // resim yükleme
    implementation(libs.coil.compose)
    // grid görünüm
    implementation (libs.accompanist.swiperefresh)
    implementation (libs.accompanist.flowlayout)

    implementation("io.github.theapache64:rebugger:1.0.0-rc03")

    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)


    implementation(libs.kotlinx.serialization.json)
    androidTestImplementation (libs.androidx.navigation.testing)

}
kapt {
    correctErrorTypes = true
}

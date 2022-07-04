import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.kdg.hilt.mvvm"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-DEV"
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        }
        create("qa") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-QA"
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${rootProject.extra["coreKtx"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["appcompat"]}")
    implementation("androidx.constraintlayout:constraintlayout:${rootProject.extra["constraintLayout"]}")
    implementation("androidx.activity:activity-ktx:${rootProject.extra["activityKtx"]}")
    implementation("com.google.android.material:material:${rootProject.extra["material"]}")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:${rootProject.extra["firebaseBom"]}"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["coroutine"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutine"]}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["daggerHilt"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["daggerHilt"]}")

    // Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["httpLogging"]}")

    // Retrofit Gson
    implementation("com.google.code.gson:gson:${rootProject.extra["gson"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra["retrofitConverter"]}")

    // Glide
    implementation("com.github.bumptech.glide:glide:${rootProject.extra["glide"]}")
    annotationProcessor("com.github.bumptech.glide:compiler:${rootProject.extra["glide"]}")

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

kapt {
    correctErrorTypes = true
}
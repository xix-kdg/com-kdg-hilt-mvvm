// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath("com.google.gms:google-services:4.3.10")
    }
}

val coreKtx by extra("1.7.0")
val activityKtx by extra("1.4.0")
val appcompat by extra("1.4.0")
val material by extra("1.4.0")
val constraintLayout by extra("2.1.2")
val coroutine by extra("1.5.2-native-mt")
val viewModel by extra("2.4.0")
val daggerHilt by extra("2.38.1")
val gson by extra("2.8.6")
val retrofitConverter by extra("2.9.0")
val httpLogging by extra("4.9.0")
val glide by extra("4.12.0")
val firebaseBom by extra("29.0.3")

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
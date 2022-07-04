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
        classpath("com.google.gms:google-services:4.3.13")
    }
}

val coreKtx by extra("1.8.0")
val activityKtx by extra("1.5.0")
val appcompat by extra("1.4.2")
val material by extra("1.6.1")
val constraintLayout by extra("2.1.4")
val coroutine by extra("1.5.2")
val viewModel by extra("2.4.0")
val daggerHilt by extra("2.38.1")
val gson by extra("2.8.8")
val retrofitConverter by extra("2.9.0")
val httpLogging by extra("4.9.0")
val glide by extra("4.13.2")
val firebaseBom by extra("29.0.3")

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
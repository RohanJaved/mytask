// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false  // Hilt Plugin
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0") // Ensure Kotlin version is correct
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48.1")  // ✅ Updated version
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")


    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

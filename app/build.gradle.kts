plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.devtoolsKsp)
    alias(libs.plugins.kotlinPluginSerialization)
}

android {
    namespace = "com.mirash.familiar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mirash.familiar"
        minSdk = 28
        targetSdk = 34
        versionCode = 4
        versionName = "1.4"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            resValue("string", "app_name", "Familiar")
//            applicationIdSuffix ".debug"
        }
        release {
            isMinifyEnabled = true
            resValue("string", "app_name", "Familiar")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.gson)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.palette.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.kotlinx.serialization.json)
}
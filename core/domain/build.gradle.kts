plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.ismaelgr.core.domain"
    compileSdk = 34
    
    defaultConfig {
        minSdk = 26
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    
    buildTypes {
        release {
            isMinifyEnabled = false
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
}

dependencies {
    // Basic
    implementation(libs.androidx.core.ktx)
    
    // Hilt
    implementation(libs.hilt)
    kapt(libs.googleHiltCompiler)
    
    // Testing
    testImplementation(libs.androidx.junit)
    testImplementation(libs.test.turbine)
    testImplementation(libs.test.mockitoframework)
    testImplementation(libs.test.mockitokotlin)
    testImplementation(libs.test.mockitomockk)
    testImplementation(libs.test.coroutineTest)
}
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Google API Client для работы с GoogleAccountCredential и AndroidHttp
    implementation("com.google.api-client:google-api-client-android:1.34.0") {
        exclude(group = "org.apache.httpcomponents")
    }
    implementation("com.google.api-client:google-api-client-gson:1.34.0")
    implementation("com.google.http-client:google-http-client-jackson2:1.40.0")
    implementation("com.google.apis:google-api-services-sheets:v4-rev20230227-2.0.0")

    // Google Play Services для GoogleAccountCredential
    implementation("com.google.android.gms:play-services-auth:20.6.0")

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

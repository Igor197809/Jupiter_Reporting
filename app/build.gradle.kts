plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.jupiter_reporting"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jupiter_reporting"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packagingOptions {
        resources {
            excludes += listOf("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    // Модули проекта
    implementation(project(":data"))
    implementation(project(":network"))
    implementation(project(":sync"))
    implementation(project(":auth"))
    implementation(project(":utils"))

    // AndroidX и Compose
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.0.1")

    // Google Material и AppCompat
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Библиотеки для Google API
    implementation("com.google.android.gms:play-services-auth:20.1.0")
    implementation("com.google.api-client:google-api-client-android:1.31.5")
    implementation("com.google.apis:google-api-services-sheets:v4-rev20240826-2.0.0")

    // Room
    implementation("androidx.room:room-runtime:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")

    // WorkManager
    implementation("androidx.work:work-runtime:2.7.1")

    // Тестирование
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
dependencies {
    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:20.6.0")

    // Библиотеки Google API Client
    implementation("com.google.api-client:google-api-client-android:1.34.0") {
        exclude(group = "org.apache.httpcomponents")
    }
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1") {
        exclude(group = "org.apache.httpcomponents")
    }
    implementation("com.google.apis:google-api-services-sheets:v4-rev20230821-2.0.0") {
        exclude(group = "org.apache.httpcomponents")
    }

    // Остальные зависимости...
}

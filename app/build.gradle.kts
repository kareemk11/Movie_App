plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.banquemisr.challenge05"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.banquemisr.challenge05"
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
val junitVersion = "4.13.2"
val hamcrestVersion = "2.2"
val archTestingVersion = "2.1.0"
val robolectricVersion = "4.8"
val androidXTestCoreVersion = "1.4.0"
val androidXTestExtKotlinRunnerVersion = "1.1.5"
val espressoVersion = "3.4.0"
val coroutinesVersion = "1.6.4"
val composeVersion = "1.5.1"
val koinVersion = "3.4.0"

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.androidx.material.icons.extended)


    // Retrofit
    implementation(libs.retrofit)
    // Gson converter (for JSON parsing)
    implementation(libs.converter.gson)



    // Coil for AsyncImage
    implementation(libs.coil.compose)


    // Koin for Android
    // Koin for Jetpack Compose
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation (libs.lottie.compose)

    // Unit testing
    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.hamcrest:hamcrest-library:2.2")
    testImplementation("androidx.arch.core:core-testing:$archTestingVersion")
    testImplementation("org.robolectric:robolectric:$robolectricVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    // AndroidX Test - JVM testing
    testImplementation("androidx.test:core-ktx:$androidXTestCoreVersion")
    // AndroidX Test - Instrumented testing
    androidTestImplementation("androidx.test.ext:junit-ktx:$androidXTestExtKotlinRunnerVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation("androidx.arch.core:core-testing:$archTestingVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    implementation("io.insert-koin:koin-androidx-compose:$koinVersion") // Correct dependency
    implementation("io.insert-koin:koin-android:$koinVersion") // Add Koin Android
    implementation("io.insert-koin:koin-android-test:$koinVersion") // For Android testing
    testImplementation("io.insert-koin:koin-test:$koinVersion") // Koin testing



}
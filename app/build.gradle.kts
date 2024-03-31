plugins {
    id("com.android.application")
}

android {
    namespace = "algonquin.cst2335.lian0122"
    compileSdk = 34

    defaultConfig {
        applicationId = "algonquin.cst2335.lian0122"
        minSdk = 22
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        buildFeatures {
            viewBinding = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Json convert
    implementation ("com.google.code.gson:gson:2.10.1")

    // plan B : Retrofit for network requests
//    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room for local database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.activity:activity:1.8.2")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Lifecycle
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // Volley
    implementation ("com.android.volley:volley:1.2.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
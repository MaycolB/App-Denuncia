plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.denunciasecuador"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.denunciasecuador"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    // --- DEPENDENCIAS PRINCIPALES ---

    // ✅ VISOR DE PDF
    implementation("com.github.mhiew:android-pdf-viewer:3.2.0-beta.1")
    // Esta librería contiene la definición de ConstraintLayout y sus atributos.
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt("androidx.room:room-compiler:2.6.1")
    // Core y Lifecycle de AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose (usa la BoM - Bill of Materials para gestionar versiones)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Navigation para Compose
    implementation("androidx.navigation:navigation-compose:2.7.7") // Usar una versión estable y consistente

    // Room (Base de datos local)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation(libs.appcompat)
// implementation(libs.androidx.appcompat)
// implementation(libs.material)
// implementation(libs.androidx.activity)
// implementation(libs.androidx.constraintlayout)
    kapt("androidx.room:room-compiler:2.6.1")

    // DataStore (almacenamiento simple)
    implementation("androidx.datastore:datastore-preferences:1.1.1") // Recomiendo una versión más reciente

    // Iconos extendidos de Material Design
    implementation("androidx.compose.material:material-icons-extended-android:1.6.7")


    // --- DEPENDENCIAS DE TEST ---

    // Pruebas Unitarias Locales (corren en tu máquina)
    testImplementation(libs.junit)

    // Pruebas de Instrumentación (corren en un dispositivo/emulador)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core) // Esta es la que da el error
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)


    // --- DEPENDENCIAS DE DEBUG ---
    // Herramientas para previsualización y depuración en Compose
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
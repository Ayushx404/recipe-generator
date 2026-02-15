import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.util.Properties

plugins {
    alias(libs.plugins.kspDevTools)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHiltAndroid)
    alias(libs.plugins.ktLint)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.androidx.room)
}

android {
    namespace = "com.dhruvv.recipegenerator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dhruvv.recipegenerator"
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
        buildFeatures {
            buildConfig = true
        }
        debug {
            buildConfigField("String", "GEMINI_API_KEY", "\"${project.getLocalProperty("gemini_api_key")}\"")
            buildConfigField("String", "GROQ_API_KEY", "\"${project.getLocalProperty("groq_api_key")}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    ktlint {
        android = true
        ignoreFailures = false
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.SARIF)
            reporter(ReporterType.CHECKSTYLE)
        }
        outputToConsole.set(true)
        outputColorName.set("YELLOW")
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

//// This block will be executed whenever a new task is added to the project.
//tasks.whenTaskAdded {
//    // Check if the task name is either "compileDebugJavaWithJavac" or "compileReleaseJavaWithJavac".
//    // These tasks are responsible for compiling the Java source files in debug and release configurations, respectively.
//    if (name == "compileDebugJavaWithJavac" || name == "compileReleaseJavaWithJavac") {
//        // Add a dependency on the "ktlintCheck" task.
//        // This means that before either of the compile tasks ("compileDebugJavaWithJavac" or "compileReleaseJavaWithJavac") can run,
//        // the "ktlintCheck" task must be completed successfully.
//        dependsOn("ktlintCheck")
//    }
//}

fun Project.getLocalProperty(
    key: String,
    file: String = "local.properties",
): String {
    val properties = Properties()
    val localPropertiesFile = rootDir.resolve(file)
    if (localPropertiesFile.isFile) {
        localPropertiesFile.inputStream().use { properties.load(it) }
    } else {
        throw IllegalStateException("File $file not found.")
    }
    return properties.getProperty(key) ?: ""
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    implementation(libs.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)

    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)

    // navigation
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

    // dagger hilt
    ksp(libs.hilt.android.compiler)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing) // For instrumented tests.

    // room
    implementation(libs.room.android)
    // To use Kotlin annotation processing tool (ksp)
    ksp(libs.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)
    // optional - Test helpers
    testImplementation(libs.room.testing)

    // coroutine
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)

    // test flow
    testImplementation(libs.turbine)
    androidTestImplementation(libs.turbine)

    // navigation
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    // gson

    // gson
    implementation(libs.gson)

    // moshi

    // gemini gen ai - removed, using Groq instead

    // moshi
    implementation(libs.moshi)

    // gemini gen ai
    implementation(libs.gemini.generative.ai)

    // retrofit for REST API
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // lottie animations
    implementation(libs.lottie.compose)
}

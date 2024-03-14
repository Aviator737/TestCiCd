import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.io.path.Path
import kotlin.io.path.bufferedReader

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.firebase.crashlitycs)
    alias(libs.plugins.firebase.appdistribution)
    alias(libs.plugins.gms.googleServices)
}

android {
    namespace = "com.example.testcicd"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testcicd"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
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
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

}

open class RunMultipleUITests : DefaultTask() {

    private val androidProject = project.android

    @TaskAction
    fun run() {
        val testClasses = if (project.hasProperty("testClasses")) {
            (project.property("testClasses") as String).split(',')
        } else {
            if (project.hasProperty("csvPath")) {
                val path = project.property("csvPath") as String
                val hasHeader =
                    (if (project.hasProperty("hasHeader")) project.property("hasHeader") as String else "true").toBooleanStrict()
                val viewsCol =
                    (if (project.hasProperty("viewsCol")) project.property("viewsCol") as String else "1").toInt()
                val terminatePercent =
                    (if (project.hasProperty("terminatePercent")) project.property("terminatePercent") as String else "100").toDouble()

                val classesWithViews = readCsv(path, hasHeader, viewsCol).filter { entry ->
                    !entry.key.contains("Activity") &&
                            entry.key.isNotEmpty() &&
                            Regex("[a-zA-Z0-9_]+").matches(entry.key)
                }

                val max = classesWithViews.maxBy { it.value }.value
                val classes = classesWithViews.mapValues { entry ->
                    val views = entry.value
                    (views * 100) / max.toDouble()
                }.filter { entry ->
                    entry.value >= terminatePercent
                }
                project.fileTree("src/androidTest").files.map {
                    it.nameWithoutExtension
                }.filter { name ->
                    classes.keys.any { name.lowercase().contains(it.lowercase()) }
                }
            } else {
                throw Exception("Property testClasses or csvPath not found")
            }
        }

        runTests(testClasses)
    }

    private fun runTests(testClasses: List<String>) {
        val adb = androidProject.adbExecutable.toString()
        val variant = androidProject.testVariants.first()
        val appId = androidProject.defaultConfig.applicationId
        val testRunner = variant.testedVariant.mergedFlavor.testInstrumentationRunner

        testClasses.forEach { testClass ->
            val command = listOf(
                adb, "shell", "am", "instrument", "-w", "-e", "class",
                "$appId.$testClass",
                "$appId.test/$testRunner"
            )

            println(command)

            val process = ProcessBuilder(command).start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                println(line)
            }
            process.waitFor()
        }
    }

    private fun readCsv(path: String, hasHeader: Boolean, viewsCol: Int): Map<String, Int> {
        val classesWithViews = mutableMapOf<String, Int>()
        var filteredRows = 1
        val reader = Path(path).bufferedReader()
        val rows = reader.lineSequence()
            .filter {
                val condition = it.isNotBlank() && !it.startsWith('#')
                if (!condition) filteredRows++
                condition
            }.toList()
        if (rows.isEmpty()) {
            throw Exception("Given csv is empty")
        }
        rows.forEachIndexed { i, row ->
            if (hasHeader && i == 0) return@forEachIndexed
            val cols = row.split(',')
            if (cols.size - 1 < viewsCol) {
                throw Exception("Invalid csv row #${i+filteredRows} (viewsCol is greater than row columns)")
            }
            val name = cols[0]
            val views = try {
                cols[viewsCol].toInt()
            } catch (e: Exception) {
                throw Exception("Invalid csv column #${viewsCol+1} at row #${i+filteredRows} (must be a number)")
            }
            classesWithViews[name] = views
        }

        return classesWithViews
    }
}

tasks.register<RunMultipleUITests>("runMultipleUITests") {
    group = "verification"
}

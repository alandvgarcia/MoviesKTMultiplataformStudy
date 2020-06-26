import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.3.70"
    id ("com.squareup.sqldelight")
    id("com.android.library")
}


android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(17)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
}


sqldelight {
    database("MovieDatabase"){
        packageName = "com.alandvgarcia.db"
        sourceFolders = listOf("sqldelight")
    }
}

kotlin {

    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "SharedCode"
            }
        }
    }

    android()

    sourceSets{
         commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("io.ktor:ktor-client-core:1.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.0-RC")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.14.0")
                implementation("io.ktor:ktor-client-serialization:1.3.1")
                implementation("com.squareup.sqldelight:runtime:1.4.0")

            }
        }

        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("io.ktor:ktor-client-android:1.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.0-RC")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")
                implementation("io.ktor:ktor-client-serialization-jvm:1.3.1")
                implementation("com.squareup.sqldelight:android-driver:1.4.0")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.0-RC")
                implementation("io.ktor:ktor-client-serialization-native:1.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:0.14.0")
                implementation("com.squareup.sqldelight:native-driver:1.4.0")
            }
        }
    }

}


val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)
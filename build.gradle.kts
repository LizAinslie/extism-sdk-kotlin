plugins {
    kotlin("multiplatform") version "1.9.20"
    id("com.android.library") version "7.3.0"
}

group = "org.extism"
version = "0.0.0-ci"

repositories {
    mavenCentral()
}

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.apply {
            binaries {
                framework {
                    baseName = "shared"
                    isStatic = true
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
        }
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }

    // todo: publish
}

// todo: doc

android {
    compileSdk = 33
    namespace = "org.extism.sdk"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    // todo: we may not need these lines, i copied this over from a lib that bundles its own resources
    sourceSets["main"].res.srcDirs(
        "src/commonMain/resources",
        "src/androidMain/resources"
    )
    sourceSets["main"].resources.srcDirs(
        "src/commonMain/resources",
        "src/androidMain/resources"
    )
    // end; see above ^

    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}
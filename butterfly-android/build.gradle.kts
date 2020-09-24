import com.lightningkite.khrysalis.gradle.KhrysalisPluginExtension

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("com.lightningkite.khrysalis:plugin:0.1.0")
    }
}

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("digital.wup.android-maven-publish") version "3.6.2"
}
apply(plugin = "com.lightningkite.khrysalis")

configure<KhrysalisPluginExtension> {
    projectName = "Butterfly"
    organizationName = "Lightning Kite"
}

group = "com.lightningkite.butterfly"
version = "0.1.1"

repositories {
    jcenter()
    mavenCentral()
    maven("https://jitpack.io")
    google()
    mavenLocal()
    maven("https://maven.google.com")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(19)
        targetSdkVersion(30)
        versionCode = 5
        versionName = "1.0.5"
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(kotlin("reflect"))
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    api("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    api("androidx.appcompat:appcompat:1.2.0")
    api("com.google.android.material:material:1.2.1")
    api("androidx.preference:preference-ktx:1.1.1")
    api("androidx.recyclerview:recyclerview:1.1.0")
    api("com.fasterxml.jackson.core:jackson-core:2.9.10")
    api("com.fasterxml.jackson.core:jackson-annotations:2.9.10")
    api("com.fasterxml.jackson.core:jackson-databind:2.9.10.6")
    api("com.squareup.okhttp3:okhttp:3.12.0")
    api("de.hdodenhof:circleimageview:2.2.0")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    api("com.romandanylyk:pageindicatorview:1.0.3")
    api("io.reactivex.rxjava2:rxkotlin:2.4.0")
    api("io.reactivex.rxjava2:rxandroid:2.1.1")
    api("com.google.android.exoplayer:exoplayer:2.11.8")
}

tasks.create("sourceJar", Jar::class) {
    classifier = "sources"
    from(android.sourceSets["main"].java.srcDirs)
    from(project.projectDir.resolve("src/include"))
}

publishing {
    publications {
        val mavenAar by creating(MavenPublication::class) {
            from(components["android"])
            artifact(tasks.getByName("sourceJar"))
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }
}

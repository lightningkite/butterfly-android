buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("maven")
    id("signing")
    id("org.jetbrains.dokka") version "1.4.20"
    `maven-publish`
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
        minSdkVersion(21)
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
    api("com.squareup.okhttp3:okhttp:3.14.9")
    api("de.hdodenhof:circleimageview:2.2.0")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    api("com.romandanylyk:pageindicatorview:1.0.3")
    api("io.reactivex.rxjava2:rxkotlin:2.4.0")
    api("io.reactivex.rxjava2:rxandroid:2.1.1")
    api("com.google.android.exoplayer:exoplayer:2.11.8")
}

tasks {
    val sourceJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets["main"].java.srcDirs)
        from(project.projectDir.resolve("src/include"))
    }
    val javadocJar by creating(Jar::class) {
        dependsOn("dokkaJavadoc")
        archiveClassifier.set("javadoc")
        from(project.file("build/dokka/javadoc"))
    }
    artifacts {
        archives(sourceJar)
        archives(javadocJar)
    }
}

afterEvaluate {
    publishing {
        publications {
            val release by creating(MavenPublication::class) {
                from(components["release"])
                artifact(tasks.getByName("sourceJar"))
                artifact(tasks.getByName("javadocJar"))
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
            }
            val debug by creating(MavenPublication::class) {
                from(components["debug"])
                artifact(tasks.getByName("sourceJar"))
                artifact(tasks.getByName("javadocJar"))
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
            }
        }
    }
    signing {
        this.sign(publishing.publications)
    }
}

tasks.named<Upload>("uploadArchives") {
    repositories.withConvention(MavenRepositoryHandlerConvention::class) {
        mavenDeployer {
            beforeDeployment {
                signing.signPom(this)
            }
        }
    }

    repositories.withGroovyBuilder {
        "mavenDeployer"{
            "repository"("url" to "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
                "authentication"("userName" to project.properties["ossrhUsername"], "password" to project.properties["ossrhPassword"])
            }
            "snapshotRepository"("url" to "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
                "authentication"("userName" to project.properties["ossrhUsername"], "password" to project.properties["ossrhPassword"])
            }
            "pom" {
                "project" {
                    setProperty("name", "Butterfly-Android")
                    setProperty("packaging", "jar")
                    setProperty("description", "A library to help with the development of android Projects")
                    setProperty("url", "https://github.com/lightningkite/butterfly-android")

                    "scm" {
                        setProperty("connection", "scm:git:https://github.com/lightningkite/butterfly-android.git")
                        setProperty("developerConnection", "scm:git:https://github.com/lightningkite/butterfly-android.git")
                        setProperty("url", "https://github.com/lightningkite/butterfly-android")
                    }

                    "licenses" {
                        "license"{
                            setProperty("name", "The MIT License (MIT)")
                            setProperty("url", "https://www.mit.edu/~amini/LICENSE.md")
                            setProperty("distribution", "repo")
                        }

                    }
                    "developers"{
                        "developer"{
                            setProperty("id", "bjsvedin")
                            setProperty("name", "Brady Svedin")
                            setProperty("email", "brady@lightningkite.com")
                        }
                    }
                }
            }
        }
    }
}

plugins {
    `kotlin-dsl`
    id("maven-publish")
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.vanniktech:gradle-maven-publish-plugin:0.29.0")
    implementation(libs.kotlin.gradle)
    implementation(libs.springBoot.gradle)
    implementation(libs.kotlin.allopen)
    implementation(libs.kotlin.noarg)
    implementation(libs.graalvm.native)
    implementation(libs.oshai.kotlinLogging)
    implementation(libs.slf4j.api)
}

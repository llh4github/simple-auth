plugins {
    `java-library`
    id("kotlin-conventions")
    id("auth-maven-publish")
    alias(libs.plugins.ksp)
}
dependencies {

    implementation(libs.yitter.idgenerator)

    implementation(platform(libs.springboot.dependencies))
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("org.springframework.boot:spring-boot-autoconfigure")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly("org.springframework:spring-web")
    compileOnly("org.springframework.boot:spring-boot-starter-data-redis")
    compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    compileOnly("org.jetbrains.kotlin:kotlin-reflect")

    implementation(libs.kotlin.allopen)
    implementation(libs.jjwt.api)
    implementation(libs.jjwt.impl)
    implementation(libs.jjwt.jackson)
}


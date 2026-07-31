plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ktor)
}

group = "net.vpg"
version = "0.0.1"

application {
    mainClass = "net.vpg.ApplicationKt"
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.caching.headers)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.cors)
    implementation(libs.vjson)

    //Logging
    implementation(libs.logback.classic)

    // Testing
    testImplementation(kotlin("test"))
    testImplementation(libs.ktor.server.test.host)
}

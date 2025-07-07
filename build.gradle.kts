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
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.vjson)
    testImplementation(libs.ktor.server.test.host)
}

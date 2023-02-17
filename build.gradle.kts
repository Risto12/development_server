val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

plugins {
    val pluginKotlinVersion = "1.8.10" // == kotlinVersion
    kotlin("jvm") version pluginKotlinVersion
    id("io.ktor.plugin") version "2.2.3" // == ktorVersion
    id("org.jetbrains.kotlin.plugin.serialization") version pluginKotlinVersion
}

group = "com.development"
version = "0.0.1"
application {
    mainClass.set("com.development.ApplicationKt")
    //mainClass.set("io.ktor.server.netty.EngineMain")
}
ktor {

    fatJar {
        archiveFileName.set("development-server.jar")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-config-yaml-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-server-websockets:$ktorVersion")
    implementation("io.ktor:ktor-server-resources:$ktorVersion")
    implementation("io.ktor:ktor-server-request-validation:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.4")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-network-tls-certificates:$ktorVersion") // to use https in development
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}
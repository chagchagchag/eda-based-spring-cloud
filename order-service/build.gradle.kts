import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.6.21" apply false
}

extra["springCloudVersion"] = "2022.0.4"

subprojects{
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-jpa")

    allOpen {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }
}

allprojects{
    group = "net.spring.cloud.prototype"
    version = "0.0.1-SNAPSHOT"

    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        api(project(":common:common-dataaccess"))
        api(project(":common:common-domain"))
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    tasks.bootBuildImage {
        builder.set("paketobuildpacks/builder-jammy-base:latest")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.getByName("bootJar"){
        enabled = false
    }

    tasks.getByName("jar"){
        enabled = true
    }
}
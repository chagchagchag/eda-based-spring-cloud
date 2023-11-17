import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.f4b6a3:ulid-creator:5.2.0")
    runtimeOnly("com.h2database:h2")
}
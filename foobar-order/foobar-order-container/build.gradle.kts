plugins{
    id("com.google.cloud.tools.jib") version "3.4.0"
}

dependencies {
    api(project(":foobar-core:foobar-core-dataaccess"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//    implementation("org.springframework.boot:spring-boot-starter-security")
//    testImplementation("org.springframework.security:spring-security-test")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("io.github.serpro69:kotlin-faker:1.15.0")
//    implementation("at.favre.lib:bcrypt:0.10.2")
}

tasks.getByName("bootJar"){
    enabled = true
}

tasks.getByName("jar"){
    enabled = false
}

jib{
    val profile : String = project.findProperty("env") as? String ?: "local"

    from {
        image = "amazoncorretto:17"

        // M1 Mac을 사용할 경우 아래 내용을 추가.
//            platforms {
//                platform {
//                    architecture = "arm64"
//                    os = "linux"
//                }
//            }
    }

    to {
        image = "chagchagchag/foobar-order-container"
        // image tag 는 여러개 지정 가능하다.
        tags = setOf("latest")
    }

    container{
        creationTime = "USE_CURRENT_TIMESTAMP"

        // jvm 옵션
        jvmFlags = listOf(
            "-Dspring.profiles.active=local",
            "-XX:+UseContainerSupport",
            "-Dserver.port=8080",
            "-Dfile.encoding=UTF-8",
        )

        // 컨테이너 입장에서 외부로 노출할 포트
        ports = listOf("8080")

        labels = mapOf(
            "maintainer" to "chagachagchag.dev@gmail.com"
        )
    }

    extraDirectories{

    }
}
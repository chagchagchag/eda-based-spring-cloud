plugins{
    id("com.google.cloud.tools.jib") version "3.4.0"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")
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
        image = "chagchagchag/foobar-display-container"
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
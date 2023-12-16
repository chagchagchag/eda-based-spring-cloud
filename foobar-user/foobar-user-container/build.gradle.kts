plugins{
    id("com.google.cloud.tools.jib") version "3.4.0"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.getByName("bootJar"){
    enabled = true
}

tasks.getByName("jar"){
    enabled = false
}

jib{
    val profile : String = System.getenv("JIB_CONTAINER_PROFILE") as? String ?: "local"
    val kafkaBootstrapServers : String = System.getenv("COMPOSE_SPRING_KAFKA_BOOTSTRAP_SERVERS") as? String ?: "localhost:19091"
    val datasourceUrl : String = System.getenv("COMPOSE_SPRING_DATASOURCE_URL") as? String ?: "localhost:3306"

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
        image = "chagchagchag/foobar-user-container"
        // image tag 는 여러개 지정 가능하다.
        tags = setOf("latest")
    }

    container{
        creationTime = "USE_CURRENT_TIMESTAMP"

        // jvm 옵션
        jvmFlags = listOf(
            "-Dspring.profiles.active=${profile}",
            "-Dspring.kafka.consumer.bootstrap-servers=${kafkaBootstrapServers}",
            "-Dspring.kafka.producer.bootstrap-servers=${kafkaBootstrapServers}",
//            "-Dspring.datasource.url=${datasourceUrl}",
            "-XX:+UseContainerSupport",
            "-XX:+UseG1GC",
            "-verbose:gc",
            "-XX:+PrintGCDetails",
            "-Dserver.port=8080",
            "-Dfile.encoding=UTF-8",
        )

        // 컨테이너 입장에서 외부로 노출할 포트
//        ports = listOf("8080")

        labels = mapOf(
            "maintainer" to "chagachagchag.dev@gmail.com"
        )
    }

    extraDirectories{

    }
}
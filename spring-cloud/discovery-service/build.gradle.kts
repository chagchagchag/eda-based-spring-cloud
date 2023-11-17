extra["springCloudVersion"] = "2022.0.4"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

dependencies{
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
}

tasks.getByName("bootJar"){
    enabled = true
}

tasks.getByName("jar"){
    enabled = false
}
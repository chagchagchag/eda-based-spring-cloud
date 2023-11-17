FROM openjdk:17-alpine AS jar-image
WORKDIR deploy
COPY build/libs/config-service-0.0.1-SNAPSHOT.jar config-service.jar
RUN java -jar -Djarmode=layertools config-service.jar extract

FROM openjdk:17-alpine
WORKDIR deploy
COPY serverKey/EncryptedConfigServerKey.jks EncryptedConfigServerKey.jks
COPY --from=jar-image deploy/dependencies/ ./
COPY --from=jar-image deploy/snapshot-dependencies/ ./
COPY --from=jar-image deploy/spring-boot-loader/ ./
COPY --from=jar-image deploy/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
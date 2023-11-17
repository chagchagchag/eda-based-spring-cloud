FROM openjdk:17-alpine AS jar-image
WORKDIR deploy
COPY catalog-container/build/libs/catalog-container-0.0.1-SNAPSHOT.jar catalog-container.jar
RUN java -jar -Djarmode=layertools catalog-container.jar extract

FROM openjdk:17-alpine
WORKDIR deploy
COPY --from=jar-image deploy/dependencies/ ./
COPY --from=jar-image deploy/snapshot-dependencies/ ./
COPY --from=jar-image deploy/spring-boot-loader/ ./
COPY --from=jar-image deploy/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
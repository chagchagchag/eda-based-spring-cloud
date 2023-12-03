FROM openjdk:17-alpine AS jar-image
WORKDIR deploy
COPY order-container/build/libs/order-container-0.0.1-SNAPSHOT.jar order-container.jar
RUN java -jar -Djarmode=layertools order-container.jar extract

FROM openjdk:17-alpine
WORKDIR deploy
COPY --from=jar-image deploy/dependencies/ ./
COPY --from=jar-image deploy/snapshot-dependencies/ ./
COPY --from=jar-image deploy/spring-boot-loader/ ./
COPY --from=jar-image deploy/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
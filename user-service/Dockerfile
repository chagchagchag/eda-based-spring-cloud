FROM openjdk:17-alpine AS jar-image
WORKDIR deploy
COPY user-container/build/libs/user-container-0.0.1-SNAPSHOT.jar user-container.jar
RUN java -jar -Djarmode=layertools user-container.jar extract

FROM openjdk:17-alpine
WORKDIR deploy
COPY --from=jar-image deploy/dependencies/ ./
COPY --from=jar-image deploy/snapshot-dependencies/ ./
COPY --from=jar-image deploy/spring-boot-loader/ ./
COPY --from=jar-image deploy/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
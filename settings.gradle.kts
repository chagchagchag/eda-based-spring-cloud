rootProject.name = "eda_based_spring_cloud"

include("common")
include("common:common-domain")
include("common:common-dataaccess")

include("spring-cloud")
include("spring-cloud:config-service")
include("spring-cloud:discovery-service")
include("spring-cloud:api-gateway")

include("infrastructure")
include("infrastructure:kafka:kafka-config-data")
include("infrastructure:kafka:kafka-model")
include("infrastructure:kafka:kafka-consumer")
include("infrastructure:kafka:kafka-producer")

include("order-service")
include("order-service:order-container")

include("user-service")
include("user-service:user-container")

include("foobar-display")
include("foobar-display:foobar-display-container")
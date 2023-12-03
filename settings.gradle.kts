rootProject.name = "eda_based_spring_cloud"

include("spring-cloud")
include("spring-cloud:config-service")
include("spring-cloud:discovery-service")
include("spring-cloud:api-gateway")

include("infrastructure")
include("infrastructure:kafka:kafka-config-data")
include("infrastructure:kafka:kafka-model")
include("infrastructure:kafka:kafka-consumer")
include("infrastructure:kafka:kafka-producer")

include("foobar-core")
include("foobar-core:foobar-core-domain")
include("foobar-core:foobar-core-dataaccess")

include("foobar-order")
include("foobar-order:foobar-order-container")

include("foobar-user")
include("foobar-user:foobar-user-container")

include("foobar-display")
include("foobar-display:foobar-display-container")
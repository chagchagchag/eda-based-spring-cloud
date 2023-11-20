
### 완료된 부분 (2023.10)

현재 User Container 와 여기에 따르는 Api Gateway 에서의 회원가입, 로그인 처리, 로컬 카프카 클러스터 도커 컴포즈 세팅작업까지 완료되어 있습니다. 회원가입, 로그인 시 사용한 인증 방식은 JWT 기반 Spring Security 방식입니다. RestDocs 또는 Swagger 를 적용해야 하는데, 이 부분은 Order Service, CatalogService  작업이 마무리 된 후 작업할 예정입니다.<br>



> **완료된 부분**<br>

- Api Gateway → User Container 
- User Container : 회원가입, JWT 로그인, 인증 (Spring Security)
- 로컬 카프카 docker-compose
  - 클러스터 3기, 주키퍼 1기, 카프카 매니저 1기 



> 



### 현재 진행중인 개발 내용 (2023.11\~)

> 10 월 버전 이후로 소스코드를 수정중이고,  원래 계획하지 않았던 AVRO 를 도입하기로 하면서 조금 늦어지고 있습니다. 아마도 11월 말 쯤에 현재 진행중인 EDA 기반의 프로젝트를 확인할 수 있게될 듯 합니다. <br>

<br>



#### 아키텍처

Order Service 와 Catalogue Service 간의 데이터 동기화를 SAGA, Outbox 방식으로 풀어내는 작업을 하고 있습니다. 예를 들면 아래와 같은 구조입니다.

<img src="./img/EDA-1.png" width="90%" height="90%"/>

<br>



주문 (POST /order) → Order 데이터베이스 → Outbox 에 저장 

- 사용자로부터 주문 저장 요청이 발생합니다. Order Service 는 이 데이터를 Order Service 가 바라보는 order 데이터베이스에 저장함과 동시에 `OrderCreatedEvent` 이벤트 객체를 생성합니다. 이렇게 생성된 이벤트를 보통 EDA 쪽의 용어에서는 도메인 이벤트라고 이야기합니다. 이렇게 생성된 이벤트 객체는 Outbox 에 저장해둡니다.

<br>



Order Service → MessageQueue

- OrderService에서 주문을 저장하는 연산은 `OrderCreatedEvent` 라는 도메인 이벤트를 발생시킵니다. 이렇게 발생된 도메인 이벤트는 메시지큐(e.g. Kafka, RabbitMQ) 에 전달됩니다. 
- 이렇게 메시지 큐에 전달하기 전에는 OrderService 내의 outbox 에 도메인 이벤트의 SAGA ID, SagaStatus, OutboxStatus 와 Event 의 내용을 저장하는 연산을 수행합니다.
- 그리고 이렇게 저장된 이벤트는 Scheduler 를 통해 일정 주기마다 한번씩 Batch 기반 전송 방식으로 메시지 큐에 전달합니다. (이번 예제에서는 단건 전송방식으로 구현합니다.)

<br>



Message Queue → Catalogue Service

- Catalogue Service 는 Kafka Listener 를 통해 메시지 큐의 메시지를 지속적으로 체크해서 수신합니다. 그리고 Kafka Listener 를 통해서 전달받은 이벤트 데이터를 수신한 즉시 outbox 에 저장합니다.

<br>



Scheduler(Catalogue Service) → Catalogue 데이터베이스

- 스케쥴러는 저장된 이벤트를 주기적으로 인출해서 Catalogue 데이터베이스에 데이터를 저장합니다.

<br>



주문데이터 조회

- 사용자는 /catalogue/order/{orderId}  API 를 접근해서 주문데이터를 조회하는데, 이때 조회하는 데이터는 catalogue-service 가 스케쥴러 기반으로 업데이트한 데이터베이스의 데이터입니다.

<br>



흔히 메시지 큐 를 통해 도메인 이벤트를 전달하는 방식을 SAGA 라고 이야기하는 편이고, 도메인 안에서 발생한 이벤트를 Outbox라는 데이터베이스에 보관해두고 이벤트 트랜잭션의 ACID를 확보하는 것을 Outbox 라고 자주 이야기합니다.<br>

<br>



#### 참고해볼만한 자료들

제가 만드는 자료는 예제 정도의 범위로 SAGA, Outbox를 어떻게 구축하는지에 대한 큰 그림을 코드로 표현하는데에 목적이 있습니다. 혼자 EDA(Event Driven Architecture) 를 스터디할 때 참고했던 자료 또는 우연히 찾았지만 아직 읽지 않은 유용해보이는 자료들은 아래와 같습니다. 혹시라도 EDA에 대해 관심이 있지만 생소하시다면 참고해주셨으면 합니다.

- [Kafka Transaction](https://yangbongsoo.tistory.com/77)
- [Kafka 이벤트 발행과 DB 저장 (redis) 트랜잭션](https://yangbongsoo.tistory.com/83)
- [Event Driven Architecture](https://jaehun2841.github.io/2019/06/23/2019-06-23-event-driven-architecture/#Event-Driven-%EB%9E%80)
- [DDD - 이벤트란 무엇인가?! + 마이크로 서비스간 트랜잭션 처리](https://jaehoney.tistory.com/254)
- [당신의 MSA 는 안녕하신가요? MSA 를 보완하는 아키텍처 : EDM (Event Driven Microservice)](https://www.samsungsds.com/kr/insights/msa_architecture_edm.html)
- [Microservices: Clean Architector, DDD, SAGA, Outbox & Kafka](https://www.udemy.com/course/microservices-clean-architecture-ddd-saga-outbox-kafka-kubernetes/)
  - 이 강의에서 제공하는 예제와 강의자료에서 가르쳐주는 SAGA, Outbox 의 흐름을 직접 코드를 보고 파악한 후 조금은 더 단순화된 버전의 예제로 만들고 있습니다. 조금만 기다려주세요 흐흑..!!
- [Object Mapper](https://yangbongsoo.tistory.com/75)
- [Microservices Patterns](https://www.oreilly.com/library/view/microservices-patterns/9781617294549/)
  - 이 책이 유명하다고 하는데 아직은 읽어보지 못했습니다.......

<br>



#### 설계, 코드 구조, ERD

휴... 기빨리네요... 시간될때 꼭 정리해두겠습니다!!<br>

<br>









### 실행

- 이 프로젝트는 로컬 개발환경에서는 Docker, Docker Compose 를 기반으로 구동합니다.
- 로컬에서 이 프로젝트를 실행시키시려면 아래의 명령어를 터미널에서 실행시켜주세요.
- (도커가 설치되어 있지 않다면 [Docker Desktop](https://www.docker.com/products/docker-desktop/) 을 설치해주세요.)

```bash
source local-build-all-docker-image.sh
```

<br>



실행된 도커 인스턴스를 정지 & 삭제하려면 `Docker Desktop` 에서 `docker-compose` 라는 이름으로 구동되는 도커컴포즈 스택을 선택해 체크한 후 상단의 `Delete` 버튼을 클릭해 중지시킵니다. 

<img src="./img/DOCKER-DESTROY.png" width="60%" height="60%"/>





### 소스 코드 파악

user-service, order-service, catalog-service 를 각각의 모듈로 취급해 각각의 MSA 들을 개별 리포지터리에서 작업이 가능하도록 모듈로 구성했고 여기에 필요한 common 모듈, infrastructure/kafka 모듈은 필요에 따라 include 할 수 있도록 독립적으로 구성해두었습니다.<br>

messaging 모듈, user-domain 모듈 등을 따로 둔 이유는 메시징과 같은 구현이 변할 때 전체 프로젝트를 변경하는 것이 아닌 개별 모듈을 변경할수 있도록 하고 외부로 노출하는 기능은 Interface로 노출해서 종속성을 줄이도록 하기 위해 멀티 모듈로 따로 구성해두었습니다.<br>

<br>

개발 초반에 빠르게 어떤 기능을 만들어보기 위해서는 프로젝트 1개로 구현하는 것이 좋은데, 이렇게 하는 방식은 이미 알고 있었기에 조금은 유지보수가 용이하게 구성하는 멀티 모듈 방식으로 실습프로젝트를 구성하기로 했습니다. 멀티 모듈을 기반으로 기능들을 구성하면 기능 별 종속도와 결합도가 조금씩 낮아집니다. <br>

이렇게 멀티모듈로 구성 시에 설계 구조를 참고했던 자료는 [CleanArchitecture, DDD](https://www.udemy.com/course/microservices-clean-architecture-ddd-saga-outbox-kafka-kubernetes/) 입니다.

<br>



#### user-service 모듈

user-service 모듈에는 아래의 5가지 모듈이 있습니다.

- user-application
  - REST Controller, RestControllerAdvice, Parameter Validation 등 Web 계층에 대한 코드
- user-container
  - `@SpringBootApplication` , application.yml, application-local.yml 등 애플리케이션의 실행을 위한 설정파일들을 모아둔 모듈
- user-dataaccesss
  - Entity, JPA Repository, JpaConfig 등 데이터 접근 코드만을 모아둔 모듈
- user-domain
  - user-application-service
    - Spring Security 기반 인증 필터, Security Config 코드, JWT 생성/분해, 데이터 매핑 (Entity → Dto, Dto → vo(Request, Response)), Repository 호출 및 트랜잭션 처리 코드들을 모아둔 모듈
  - user-domain-core
    - DDD 적용시 domain event, exception 등을 구현하기 위한 모듈
- user-messaging
  - 메시징을 위한 모듈.
  - infrastructure/kafka 아래의 kafka 모듈들을 활용해 user-service 에서 수행하는 메시징 로직들을 정의하는 모듈

<br>



#### order-service 모듈

<br>



#### catalog-service 모듈

<br>



### 포트 사용 현황

#### infrastructure/docker-compose (kafka)
- 2181 : zookeeper
- 8081 : schema registry
    - depends_on : broker1, broker2, broker3
- 19092 : broker1
- 29092 : broker2
- 39092 : broker3
- 9000 : kafka-manager

<br>



#### spring-cloud/discovery-server
- 8761

#### spring-cloud/api-gateway
- 8000

#### spring-cloud/config-server
- 9999

#### user-service/user-container
- 기본적으로 애플리케이션 인스턴스는 모두 random.port 기반으로 하고 Eureka Server(Discovery Server) 로부터 fqdn(프로토콜:서버주소:포트)을 얻어오도록 규칙을 정함<br>



#### order-service/order-container
- 기본적으로 애플리케이션 인스턴스는 모두 random.port 기반으로 하고 Eureka Server(Discovery Server) 로부터 fqdn(프로토콜:서버주소:포트)을 얻어오도록 규칙을 정함<br>



#### catalog-service/catalog-container
- 기본적으로 애플리케이션 인스턴스는 모두 random.port 기반으로 하고 Eureka Server(Discovery Server) 로부터 fqdn(프로토콜:서버주소:포트)을 얻어오도록 규칙을 정함<br>



### 이 예제 프로젝트의 목적

[CleanArchitecture, DDD](https://www.udemy.com/course/microservices-clean-architecture-ddd-saga-outbox-kafka-kubernetes/) 에서 제공하는 SAGA, Outbox 기반의 멀티모듈 방식과

[Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4/dashboard) 에서 제공하는 Spring Cloud 적용방식을 

조합한 예제를 만드는 것이 목적

<br>



예를 들면 [Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4/dashboard) 에서 사용자 추가를 하는 로직의 흐름은 아래와 같습니다. 

<img src="./img/1.png" width="60%" height="60%"/>

Spring Cloud 를 어떻게 사용하는지에 대한 레시피 성격의 강의이기에 굉장히 짧은 예제로 직관적으로 설명합니다. 다만, Mapper 관련 로직들도 대부분 컨트롤러에 하드코딩 되는 등 테스트코드를 작성하거나 테스트 가능한(Testable) 구조는 아닙니다. 당연히 레시피 용도의 코드는 실무 적용시에는 응용이 필요한 예제입니다.

<br>



아래의 경우는 [CleanArchitecture, DDD](https://www.udemy.com/course/microservices-clean-architecture-ddd-saga-outbox-kafka-kubernetes/) 에서 제공하는 음식주문시스템에서 주문을 내릴 때의 구조입니다.

<img src="./img/2.png" width="70%" height="70%"/>

멀티 모듈 기반으로 구성되어있으며, Command 를 통해 Domain 에 메시지(객체의 함수)를 호출하고, 그 이후의 처리는 `OrerCreatedEvent` 라는 이벤트 기반의 처리를 합니다. 위의 그림에서 persistOrder(CreateOrderCommand) 내에서 `validateAndInitiateOrder()` 메서드 호출 이후에도 다른 동작을 수행하는데, 이 동작들은 지면상 생략을 해두었습니다.<br>

<br>



이 리포지터리에서 정리하는 예제는 이런 내용입니다. Spring Cloud 를 기반으로 SAGA, Outbox 를 직접 구현해서 예제로 만들어둡니다.<br>

<br>



### API

> 흑... swagger 를 조만간 도입할 예정!!!

#### user-container

`user-container` 의 경우 REST API 규격을 맞춰서 자원의 행위(Method)를 HTTP Method 로 표현하는 겻이 정석적인 표현이긴 하지만,<br>

사용자의 회원가입, 로그인 등에 이런 행위를 끼워맞추는 것이 부자연스러워 보이기에 불가피하게 아래와 같이 `/welcome`, `/signup`, `/login`, `/logout` 등의 독립적인 URL 을 가지도록 지정했습니다.<br>



- GET `/welcome`
- POST `/signup`
- POST `/login`
- GET `/users/{userId}`

<br>



#### order-container

현재 Kafka Connect 도커설정 + Order Container, Catalog Container, 카프카 토픽 설정 작업을 하다가 중지했습니다.

개인적인 일정으로 인해 10/18 이 후에 작업 예정입니다.<br>

<br>



#### catalog-container

현재 Kafka Connect 도커설정 + Order Container, Catalog Container, 카프카 토픽 설정 작업을 하다가 중지했습니다.

개인적인 일정으로 인해 10/18 이 후에 작업 예정입니다.<br>

<br>


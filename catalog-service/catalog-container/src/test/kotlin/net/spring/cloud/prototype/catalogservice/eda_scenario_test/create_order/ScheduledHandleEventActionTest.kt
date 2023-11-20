package net.spring.cloud.prototype.catalogservice.eda_scenario_test.create_order

import net.spring.cloud.prototype.catalogservice.dataaccess.CatalogDataAccessHelper
import net.spring.cloud.prototype.catalogservice.dataaccess.repository.CatalogJpaRepository
import net.spring.cloud.prototype.catalogservice.domain.CatalogOutboxHelper
import net.spring.cloud.prototype.catalogservice.domain.outbox.CatalogOutboxRepositoryHelper
import net.spring.cloud.prototype.catalogservice.domain.outbox.repository.CatalogOutboxRepository
import net.spring.cloud.prototype.catalogservice.fixtures.CatalogOutboxEntityFixtures
import net.spring.cloud.prototype.catalogservice.kafka.ObjectMapperUtils
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ScheduledHandleEventActionTest {

    /**
     * CatalogOutboxScheduler 는 주기적으로 OrderOutboxHelper 를 통해 handleOrderCreatedEvent() 를 호출한다.
     * - (SQL 검증) OrderOutboxRepository 의 findAllCreatedOrderEvent() 쿼리 검증
     *   : domain/outbox/repository/CatalogOutboxRepositoryTest 에 작성
     * - (유효성 체크 검증) CatalogOutboxEntity 의 payload 가 null 일때 IllegalArgumentException
     * - (유효성 체크 검증) CatalogOutboxEntity 의 payload 가 공백문자일 때 IllegalArgumentException
     * - (전송 구문 호출 여부 검증) OrderCreatedEventPublisher 로 Kafka 메시지 큐에 데이터 전송 여부 검증
     */

    @Mock
    private lateinit var objectMapperUtils: ObjectMapperUtils

    @Mock
    private lateinit var catalogOutboxRepository: CatalogOutboxRepository

    @Mock
    private lateinit var catalogJpaRepository: CatalogJpaRepository

    @Mock
    private lateinit var catalogDataAccessHelper: CatalogDataAccessHelper

    @Mock
    private lateinit var catalogOutboxRepositoryHelper: CatalogOutboxRepositoryHelper

    @InjectMocks
    private lateinit var catalogOutboxHelper: CatalogOutboxHelper

    val EMPTY_PAYLOAD_EVENT_STRING_EXCEPTION_MESSAGE = "SagaID 에 대해 outboxEntity 가 비어있습니다."

    @Test
    @DisplayName("(유효성 체크 검증) CatalogOutboxEntity 의 payload 가 null 일때 IllegalArgumentException")
    fun `(유효성 체크 검증) CatalogOutboxEntity 의 payload 가 null 일때 IllegalArgumentException`(){
        // given
        val nullPayloadEntityList = CatalogOutboxEntityFixtures
            .randomNullPayloadEntityList5()

        // when
        Mockito
            .`when`(catalogOutboxRepositoryHelper.findAllCreatedOrderEvent())
            .thenReturn(nullPayloadEntityList)

        // then
        Assertions
            .assertThatThrownBy { catalogOutboxHelper.handleOrderCreatedEvent() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(EMPTY_PAYLOAD_EVENT_STRING_EXCEPTION_MESSAGE)
    }

    @Test
    @DisplayName("(유효성 체크 검증) CatalogOutboxEntity 의 payload 가 공백문자일 일때 IllegalArgumentException")
    fun `(유효성 체크 검증) CatalogOutboxEntity 의 payload 가 공백문자일 일때 IllegalArgumentException`(){
        // given
        val emptyPayloadEntityList = CatalogOutboxEntityFixtures
            .randomEmptyStringPayloadEntityList5()

        // when
        Mockito
            .`when`(catalogOutboxRepositoryHelper.findAllCreatedOrderEvent())
            .thenReturn(emptyPayloadEntityList)

        // then
        Assertions
            .assertThatThrownBy { catalogOutboxHelper.handleOrderCreatedEvent() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(EMPTY_PAYLOAD_EVENT_STRING_EXCEPTION_MESSAGE)
    }
}
package net.spring.cloud.prototype.orderservice.eda_scenario_test.create_order

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ScheduledPublishingResultTest {

    /**
     * - (전송 성공 후 처리 결과값 검증) OutboxStatus 는 FINISHED, SagaStatus 는 FINISHED 검증
     * - (전송 실패 후 처리 결과값 검증) OutboxStatus 는 PENDING, SagaStatus 는 PENDING 검증
     */

    @Test
    @DisplayName("(전송 성공 후 처리 결과값 검증) OutboxStatus 는 FINISHED, SagaStatus 는 FINISHED 검증")
    fun `(전송 성공 후 처리 결과값 검증) OutboxStatus 는 FINISHED, SagaStatus 는 FINISHED 검증`(){

    }

    @Test
    @DisplayName("(전송 실패 후 처리 결과값 검증) OutboxStatus 는 PENDING, SagaStatus 는 PENDING 검증")
    fun `(전송 실패 후 처리 결과값 검증) OutboxStatus 는 PENDING, SagaStatus 는 PENDING 검증`(){

    }

}
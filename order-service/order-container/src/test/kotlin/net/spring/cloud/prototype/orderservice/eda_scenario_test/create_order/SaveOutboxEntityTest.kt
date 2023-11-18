package net.spring.cloud.prototype.orderservice.eda_scenario_test.create_order

import org.junit.jupiter.api.Test

class SaveOutboxEntityTest {

    /**
     * OrderDomainService 의 동작을 검증한다.
     * OrderCommandHandler 는 OrderCreateHelper (dataaccess) 의 createOrder 를 호출하는데,
     * OrderCreateHelper 는 createOrder(command) 호출에 대해 OrderEntity 저장 + OrderCreatedEvent 생성 & Outbox 에 저장 을 수행한다.
     *  OrderCreatedEvent 생성 & Outbox 에 저장 :
     *      (domain) saveOrderCreatedEventAtOutbox(command) 메서드가 그 역할을 수행한다.
     *
     *  OrderCreatedEvent 객체에 대해서 OrderOutboxRepository 에 OrderOutboxEntity 가 저장되는지 검증한다.
     *
     *
     *  참고)
     *  이 외의 유효성 검증이 필요한데, 아직까지는 기획 요건이 확립되지 않아서 유효성 검증 기능과 별도의 컴포넌트 구성은 생략해두었고,
     *  추후 추가 개발 계획이 필요하면 해당 기능 추가와 테스트 코드 추가 예정.
     */


    @Test
    fun `OrderCreatedEvent 객체에 대해서 OrderOutboxRepository 에 OrderOutboxEntity 가 저장되는지 검증한다`(){

    }

}
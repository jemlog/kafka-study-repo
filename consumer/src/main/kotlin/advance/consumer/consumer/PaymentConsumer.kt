package advance.consumer.consumer

import advance.consumer.repository.DltEvent
import advance.consumer.repository.DltEventRepository
import advance.consumer.repository.PaymentHistory
import advance.consumer.repository.PaymentHistoryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.DltStrategy
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
@Transactional
class PaymentConsumer(val paymentHistoryRepository: PaymentHistoryRepository, val dltEventRepository: DltEventRepository) {

    val log: Logger = LoggerFactory.getLogger(PaymentConsumer::class.java)

    /*
    만약 데이터 저장소가 트랜잭션을 지원한다면, @Transactional로 트랜잭션 관리하면 되지 않나?
    트랜잭션 지원 안하는 DB의 경우, 유니크 키 & Upsert 등의 방식으로 데이터 중복 처리 방지
     */
    @RetryableTopic(
        attempts = "5",
        backoff = Backoff(value = 3000),
        kafkaTemplate = "retryableTopicKafkaTemplate",
        dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(groupId = "consumer-group-1", topics = ["order-created-topic"])
    fun listen(@Payload message: Long){

        paymentHistoryRepository.save(PaymentHistory(message, LocalDateTime.now()))
        throw IllegalArgumentException()
    }

    /*
    DltStrategy.ALWAYS_RETRY_ON_ERROR 전략에서는 DLT 내부에서 에러나면 무한 재시도
     */
    @DltHandler
    fun dltHandler(@Payload message: Long){
        log.info("order-created-topic dlt occur, message : {}", message)
        dltEventRepository.save(DltEvent(message, LocalDateTime.now()))
        throw IllegalArgumentException()
    }
}
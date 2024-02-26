package advance.producer.producer

import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderProducer(val kafkaTemplate: KafkaTemplate<String, Long>) {

    companion object{
        val ORDER_CREATED_TOPIC = "order-created-topic"
    }

    @Transactional
    fun send(orderId: Long){


            kafkaTemplate.send(ProducerRecord(ORDER_CREATED_TOPIC, orderId))

    }
}
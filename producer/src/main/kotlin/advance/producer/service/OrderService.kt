package advance.producer.service

import advance.producer.producer.OrderProducer
import advance.producer.repository.OrderEntity
import advance.producer.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(val orderProducer: OrderProducer, val orderRepository: OrderRepository) {

    /*
    @Transactional을 사용하고, transaction producer를 사용하면 DB 트랜잭션과 묶어서 사용 가능하다
    실제 트랜잭션 정상 커밋 되야 메세지를 전송한다
     */
    @Transactional
    fun createOrder(productId: String, quantity: Int): Long{

        val savedOrder = orderRepository.save(OrderEntity(productId, quantity))
        orderProducer.send(savedOrder.id!!)

        if(quantity == 100){
            throw IllegalArgumentException()
        }

        return savedOrder.id
    }
}
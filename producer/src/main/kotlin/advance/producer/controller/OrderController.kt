package advance.producer.controller

import advance.producer.controller.dto.CreateOrderRequest
import advance.producer.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(val orderService: OrderService) {

    @PostMapping("/api/v1/orders")
    fun createOrder(@RequestBody createOrderRequest: CreateOrderRequest): ResponseEntity<Long>{

        val orderId = orderService.createOrder(createOrderRequest.productId, createOrderRequest.quantity)
        return ResponseEntity.ok(orderId)
    }
}
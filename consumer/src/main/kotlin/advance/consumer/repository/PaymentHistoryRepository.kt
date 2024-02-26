package advance.consumer.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentHistoryRepository : JpaRepository<PaymentHistory, Long> {
}
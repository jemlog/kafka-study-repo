package advance.consumer.repository

import org.springframework.data.jpa.repository.JpaRepository

interface DltEventRepository : JpaRepository<DltEvent, Long> {
}
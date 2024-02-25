package advance.kafkastudyrepo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaStudyRepoApplication

fun main(args: Array<String>) {
    runApplication<KafkaStudyRepoApplication>(*args)
}

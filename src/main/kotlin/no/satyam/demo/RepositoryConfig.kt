package no.satyam.demo

import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent
import java.util.concurrent.atomic.AtomicLong

@Configuration
@EnableJdbcRepositories(basePackages = ["no.satyam.demo"])
class RepositoryConfig {
    val customerId: AtomicLong = AtomicLong(0)

    @Bean
    fun isSetting(): ApplicationListener<BeforeSaveEvent<Object>> {
        return ApplicationListener { event ->
            val entity: BasicEntity = event.entity as BasicEntity

            if (entity is Customer && entity.id == null) {
                entity.id = customerId.incrementAndGet()
            }
        }
    }
}
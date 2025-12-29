package com.finalytics.infrastructure

import com.finalytics.config.AppConfig
import com.finalytics.config.DataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.nio.file.Path

@Configuration
class RepositoryFactory(private val appConfig: AppConfig) {

    @Bean
    fun transactionRepository(): TransactionRepository {
        return when (appConfig.dataSource) {
            DataSource.CSV -> {
                val resource = ClassPathResource(appConfig.csvPath)
                CsvTransactionRepository(Path.of(resource.uri))
            }
            DataSource.GCS -> throw NotImplementedError("GCS repository not yet implemented")
        }
    }
}

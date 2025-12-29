package com.finalytics.unit

import com.finalytics.config.AppConfig
import com.finalytics.config.DataSource
import com.finalytics.infrastructure.CsvTransactionRepository
import com.finalytics.infrastructure.RepositoryFactory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertIs

class RepositoryFactoryTest {

    @Test
    fun `creates CSV repository when data source is CSV`() {
        val appConfig = AppConfig(dataSource = DataSource.CSV, csvPath = "data/TX_example.csv")
        val factory = RepositoryFactory(appConfig)

        val repository = factory.transactionRepository()

        assertIs<CsvTransactionRepository>(repository)
    }

    @Test
    fun `throws NotImplementedError when data source is GCS`() {
        val appConfig = AppConfig(dataSource = DataSource.GCS, csvPath = "data/TX_example.csv")
        val factory = RepositoryFactory(appConfig)

        assertThrows<NotImplementedError> {
            factory.transactionRepository()
        }
    }
}

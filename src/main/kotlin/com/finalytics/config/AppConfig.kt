package com.finalytics.config

import org.springframework.boot.context.properties.ConfigurationProperties

enum class DataSource {
    CSV,
    GCS
}

@ConfigurationProperties(prefix = "app")
data class AppConfig(
    val dataSource: DataSource = DataSource.CSV,
    val csvPath: String = "data/TX_example.csv"
)

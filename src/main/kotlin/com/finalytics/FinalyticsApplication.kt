package com.finalytics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FinalyticsApplication

fun main(args: Array<String>) {
    runApplication<FinalyticsApplication>(*args)
}

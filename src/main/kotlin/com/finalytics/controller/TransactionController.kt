package com.finalytics.controller

import com.finalytics.service.TransactionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController(private val transactionService: TransactionService) {

    @GetMapping("/transactions")
    fun getTransactions(): List<Map<String, Any?>> {
        return transactionService.getAllTransactions().map { it.toMap() }
    }
}

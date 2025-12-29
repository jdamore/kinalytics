package com.finalytics.service

import com.finalytics.domain.Transaction
import com.finalytics.infrastructure.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionService(private val repository: TransactionRepository) {

    fun getAllTransactions(): List<Transaction> {
        return repository.getAll()
    }
}

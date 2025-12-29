package com.finalytics.infrastructure

import com.finalytics.domain.Transaction

interface TransactionRepository {
    fun getAll(): List<Transaction>
}

package com.finalytics.unit

import com.finalytics.service.TransactionService
import com.finalytics.domain.Transaction
import com.finalytics.infrastructure.TransactionRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.assertEquals

class TransactionServiceTest {

    @Test
    fun `getAllTransactions returns repository results`() {
        val mockRepository = mockk<TransactionRepository>()
        val expectedTransactions = listOf(
            Transaction(
                bookingDate = LocalDate.of(2024, 1, 1),
                partnerName = "Test",
                amount = BigDecimal("100.00"),
                currency = "EUR",
                bookingDetails = "Test details",
                paymentReference = null
            )
        )
        every { mockRepository.getAll() } returns expectedTransactions
        val service = TransactionService(mockRepository)

        val result = service.getAllTransactions()

        assertEquals(expectedTransactions, result)
        verify(exactly = 1) { mockRepository.getAll() }
    }

    @Test
    fun `getAllTransactions returns empty list when no transactions`() {
        val mockRepository = mockk<TransactionRepository>()
        every { mockRepository.getAll() } returns emptyList()
        val service = TransactionService(mockRepository)

        val result = service.getAllTransactions()

        assertEquals(emptyList(), result)
    }
}

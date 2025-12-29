package com.finalytics.unit

import com.finalytics.infrastructure.CsvTransactionRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CsvTransactionRepositoryTest {

    @TempDir
    lateinit var tempDir: Path

    private lateinit var csvFile: Path
    private lateinit var repository: CsvTransactionRepository

    @BeforeEach
    fun setUp() {
        val content = """"Booking Date","Partner Name","Partner IBAN","BIC/SWIFT","Partner Account Number","Bank code","Amount","Currency","Booking details","Payment Reference"
"15.03.2024","Test Partner","","","","","-50.00","EUR","Test payment",""
"20.03.2024","Another Partner","DE123456789","","","","1,234.56","EUR","Income","REF123""""

        csvFile = tempDir.resolve("test.csv")
        Files.write(csvFile, content.toByteArray(Charsets.UTF_16))
        repository = CsvTransactionRepository(csvFile)
    }

    @Test
    fun `getAll returns list of transactions`() {
        val transactions = repository.getAll()

        assertEquals(2, transactions.size)
    }

    @Test
    fun `parses booking date correctly`() {
        val transactions = repository.getAll()

        assertEquals(LocalDate.of(2024, 3, 15), transactions[0].bookingDate)
        assertEquals(LocalDate.of(2024, 3, 20), transactions[1].bookingDate)
    }

    @Test
    fun `parses partner name`() {
        val transactions = repository.getAll()

        assertEquals("Test Partner", transactions[0].partnerName)
        assertEquals("Another Partner", transactions[1].partnerName)
    }

    @Test
    fun `parses negative amount`() {
        val transactions = repository.getAll()

        assertEquals(BigDecimal("-50.00"), transactions[0].amount)
    }

    @Test
    fun `parses amount with thousands separator`() {
        val transactions = repository.getAll()

        assertEquals(BigDecimal("1234.56"), transactions[1].amount)
    }

    @Test
    fun `parses currency`() {
        val transactions = repository.getAll()

        assertEquals("EUR", transactions[0].currency)
    }

    @Test
    fun `parses booking details`() {
        val transactions = repository.getAll()

        assertEquals("Test payment", transactions[0].bookingDetails)
    }

    @Test
    fun `parses payment reference`() {
        val transactions = repository.getAll()

        assertNull(transactions[0].paymentReference)
        assertEquals("REF123", transactions[1].paymentReference)
    }
}

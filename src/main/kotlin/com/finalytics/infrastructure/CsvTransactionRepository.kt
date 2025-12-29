package com.finalytics.infrastructure

import com.finalytics.domain.Transaction
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CsvTransactionRepository(private val filePath: Path) : TransactionRepository {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    override fun getAll(): List<Transaction> {
        val transactions = mutableListOf<Transaction>()

        BufferedReader(
            InputStreamReader(
                Files.newInputStream(filePath),
                StandardCharsets.UTF_16
            )
        ).use { reader ->
            val headerLine = reader.readLine() ?: return emptyList()
            val headers = parseCsvLine(headerLine)
            val headerIndex = headers.mapIndexed { index, header -> header to index }.toMap()

            reader.lineSequence().forEach { line ->
                if (line.isNotBlank()) {
                    val values = parseCsvLine(line)
                    transactions.add(rowToTransaction(values, headerIndex))
                }
            }
        }

        return transactions
    }

    private fun parseCsvLine(line: String): List<String> {
        val values = mutableListOf<String>()
        var current = StringBuilder()
        var inQuotes = false

        for (char in line) {
            when {
                char == '"' -> inQuotes = !inQuotes
                char == ',' && !inQuotes -> {
                    values.add(current.toString().trim())
                    current = StringBuilder()
                }
                else -> current.append(char)
            }
        }
        values.add(current.toString().trim())

        return values
    }

    private fun rowToTransaction(values: List<String>, headerIndex: Map<String, Int>): Transaction {
        val bookingDateStr = values[headerIndex["Booking Date"]!!]
        val bookingDate = LocalDate.parse(bookingDateStr, dateFormatter)

        val amountStr = values[headerIndex["Amount"]!!].replace(",", "")
        val amount = BigDecimal(amountStr)

        val paymentReference = values[headerIndex["Payment Reference"]!!]

        return Transaction(
            bookingDate = bookingDate,
            partnerName = values[headerIndex["Partner Name"]!!],
            amount = amount,
            currency = values[headerIndex["Currency"]!!],
            bookingDetails = values[headerIndex["Booking details"]!!],
            paymentReference = paymentReference.ifEmpty { null }
        )
    }
}

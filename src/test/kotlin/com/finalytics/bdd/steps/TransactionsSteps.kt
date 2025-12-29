package com.finalytics.bdd.steps

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.cucumber.java.en.And
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TransactionsSteps {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private var response: ResponseEntity<String>? = null
    private var transactions: List<Map<String, Any?>>? = null

    private val objectMapper = jacksonObjectMapper()

    @And("transactions exist in the data file")
    fun transactionsExistInTheDataFile() {
        // Transactions are loaded from the CSV file configured in application-local.yml
    }

    @When("I request the transactions endpoint")
    fun iRequestTheTransactionsEndpoint() {
        response = restTemplate.getForEntity("/transactions", String::class.java)
    }

    @Then("I should receive a list of transactions")
    fun iShouldReceiveAListOfTransactions() {
        assertNotNull(response)
        assertEquals(200, response!!.statusCode.value())
        transactions = objectMapper.readValue(response!!.body!!)
        assertTrue(transactions!!.isNotEmpty())
    }

    @And("each transaction should have booking date, partner name, amount, and currency")
    fun eachTransactionShouldHaveRequiredFields() {
        val requiredFields = listOf("booking_date", "partner_name", "amount", "currency")
        for (transaction in transactions!!) {
            for (field in requiredFields) {
                assertTrue(transaction.containsKey(field), "Missing field: $field")
            }
        }
    }
}

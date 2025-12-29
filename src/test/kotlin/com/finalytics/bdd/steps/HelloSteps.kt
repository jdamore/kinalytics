package com.finalytics.bdd.steps

import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class HelloSteps {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private var response: ResponseEntity<String>? = null

    @Given("the application is running")
    fun theApplicationIsRunning() {
        // Application is started by SpringBootTest
    }

    @When("I visit the homepage")
    fun iVisitTheHomepage() {
        response = restTemplate.getForEntity("/", String::class.java)
    }

    @Then("I should see {string}")
    fun iShouldSee(expected: String) {
        assertNotNull(response)
        assertEquals(200, response!!.statusCode.value())
        assertEquals(expected, response!!.body)
    }
}

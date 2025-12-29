package com.finalytics.domain

import java.math.BigDecimal
import java.time.LocalDate

data class Transaction(
    val bookingDate: LocalDate,
    val partnerName: String,
    val amount: BigDecimal,
    val currency: String,
    val bookingDetails: String,
    val paymentReference: String?
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "booking_date" to bookingDate.toString(),
        "partner_name" to partnerName,
        "amount" to amount.toString(),
        "currency" to currency,
        "booking_details" to bookingDetails,
        "payment_reference" to paymentReference
    )
}

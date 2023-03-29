package com.dicoding.productdetail

import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.withNumberingFormat(): String {
    return NumberFormat.getNumberInstance().format(this.toDouble())
}

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    val date = format.parse(this) as Date
    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun String.withCurrencyFormat(): String {
    val rupiahExchangeRate = 15058
    val euroExchangeRate = 0.92

    val originalPrice = this.toDouble()
    val price = when (Locale.getDefault().country) {
        "ES" -> originalPrice / rupiahExchangeRate * euroExchangeRate
        "ID" -> originalPrice
        else -> originalPrice / rupiahExchangeRate
    }

    return NumberFormat.getCurrencyInstance().format(price)
}
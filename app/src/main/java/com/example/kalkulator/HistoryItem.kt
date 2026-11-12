package com.example.kalkulator

import java.io.Serializable

data class HistoryItem(
    val expression: String,
    val isNegative: Boolean
) : Serializable

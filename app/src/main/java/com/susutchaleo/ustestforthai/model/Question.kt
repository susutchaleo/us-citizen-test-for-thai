package com.susutchaleo.ustestforthai.model

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val answer: String,
    val thai: String
)

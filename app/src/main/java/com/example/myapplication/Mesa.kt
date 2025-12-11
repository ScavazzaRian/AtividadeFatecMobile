package com.example.myapplication

data class Mesa(
    val id: String,
    val numero: Int,
    val lugares: Int
) {
    init {
        require(numero > 0) { "O número da mesa deve ser maior que zero." }
        require(lugares > 0) { "A quantidade de lugares deve ser maior que zero." }
    }
}

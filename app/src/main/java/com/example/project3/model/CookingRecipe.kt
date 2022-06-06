package com.example.project3.model

data class CookingRecipe(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)
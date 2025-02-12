package com.example.raspsecuritytester.model

data class MenuItem(
    val title: String,
    val description: String,
    val onClick: () -> Unit
)

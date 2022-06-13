package com.bantutani.application.data.entitiy.user

data class DataLogin(
    val image: String,
    val id: Int,
    val email: String,
    val username: String,
    val fullName: String,
    val status: String,
    val token: String
)

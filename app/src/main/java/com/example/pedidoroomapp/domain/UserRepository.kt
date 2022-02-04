package com.example.pedidoroomapp.domain

import com.example.pedidoroomapp.data.model.User

interface UserRepository {

    suspend fun getUser(user: String, pass: String): User

    suspend fun insert(user: User)
}
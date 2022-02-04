package com.example.pedidoroomapp.domain

import com.example.pedidoroomapp.data.local.UserDao
import com.example.pedidoroomapp.data.model.User

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {

    override suspend fun getUser(user: String, pass: String): User =  userDao.getUser(user,pass)

    override suspend fun insert(user: User) = userDao.insert(user)
}
package com.example.pedidoroomapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val user: String,
    val pass: String,
    val pathPicture: String = ""
)
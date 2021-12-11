package com.example.pedidoroomapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PedidoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val numPed: Int,
    val canArt: Int,
    val cantArtEnt: Int,
    val detail: String,
    val date: String
)
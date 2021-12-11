package com.example.pedidoroomapp.data.model

import androidx.room.Embedded
import androidx.room.Relation


data class PedidoWithImage(
    @Embedded
    val pedidoEntity: PedidoEntity,
    @Relation(
        parentColumn = "numPed",
        entityColumn = "numPed"
    )
    val images: List<Image>
)
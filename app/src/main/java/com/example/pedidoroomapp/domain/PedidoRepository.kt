package com.example.pedidoroomapp.domain

import com.example.pedidoroomapp.data.model.Image
import com.example.pedidoroomapp.data.model.PedidoEntity
import com.example.pedidoroomapp.data.model.PedidoWithImage
import kotlinx.coroutines.flow.Flow

interface PedidoRepository{

    fun getAll(): Flow<List<PedidoWithImage>>
    fun getPedido(numPed: Int): Flow<List<PedidoWithImage>>
    suspend fun savePedido(pedidoEntity: PedidoEntity)
    suspend fun saveImage(images: List<Image>)
    suspend fun delete(pedido:PedidoEntity)

}
package com.example.pedidoroomapp.domain

import com.example.pedidoroomapp.data.local.PedidoDao
import com.example.pedidoroomapp.data.model.Image
import com.example.pedidoroomapp.data.model.PedidoEntity
import com.example.pedidoroomapp.data.model.PedidoWithImage
import kotlinx.coroutines.flow.Flow

class PedidoRepositoryImpl(private val dao: PedidoDao): PedidoRepository {

    override fun getAll(): Flow<List<PedidoWithImage>> {
        return dao.getAll()
    }

    override fun getPedido(numPed: Int): Flow<List<PedidoWithImage>> {
       return dao.getPedido(numPed)
    }

    override suspend fun savePedido(pedidoEntity: PedidoEntity) {
        dao.savePedido(pedidoEntity)
    }

    override suspend fun saveImage(images: List<Image>) {
        dao.saveImages(images)
    }

    override suspend fun delete(pedido: PedidoEntity) {
        dao.delete(pedido)
    }


}
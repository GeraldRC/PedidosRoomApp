package com.example.pedidoroomapp.data.local

import androidx.room.*
import com.example.pedidoroomapp.data.model.Image
import com.example.pedidoroomapp.data.model.PedidoEntity
import com.example.pedidoroomapp.data.model.PedidoWithImage
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {

    @Transaction
    @Query("SELECT  * FROM PedidoEntity ORDER BY id DESC")
    fun getAll(): Flow<List<PedidoWithImage>>

    @Transaction
    @Query("SELECT * FROM pedidoentity where numPed = :numped")
    fun getPedido(numped: Int): Flow<List<PedidoWithImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePedido(pedido: PedidoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveImages(images: List<Image>)

    @Delete
    suspend fun delete(pedido: PedidoEntity)
}
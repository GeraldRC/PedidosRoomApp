package com.example.pedidoroomapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.pedidoroomapp.data.model.PedidoEntity
import com.example.pedidoroomapp.data.model.PedidoWithImage
import com.example.pedidoroomapp.domain.PedidoRepository
import kotlinx.coroutines.flow.Flow

class PedidoDetailViewModel(private val repo: PedidoRepository): ViewModel() {

    fun getPedido(numPed: Int) : LiveData<List<PedidoWithImage>> {
        return repo.getPedido(numPed).asLiveData()
    }

    suspend fun delete(pedido: PedidoEntity){
        repo.delete(pedido)
    }

}

class PedidoDetailViewModelFactory(private val repo: PedidoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PedidoRepository::class.java).newInstance(repo)
    }
}
package com.example.pedidoroomapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.pedidoroomapp.data.model.PedidoEntity
import com.example.pedidoroomapp.data.model.PedidoWithImage
import com.example.pedidoroomapp.domain.PedidoRepository

class PedidoViewModel(private val repo: PedidoRepository): ViewModel() {

    val allPedido: LiveData<List<PedidoWithImage>> = repo.getAll().asLiveData()
}

class PedidoViewModelFactory(private val repo: PedidoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  modelClass.getConstructor(PedidoRepository::class.java).newInstance(repo)
    }
}
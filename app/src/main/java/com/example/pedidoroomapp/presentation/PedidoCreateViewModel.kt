package com.example.pedidoroomapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pedidoroomapp.data.model.Image
import com.example.pedidoroomapp.data.model.PedidoEntity
import com.example.pedidoroomapp.domain.PedidoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PedidoCreateViewModel(private val repo: PedidoRepository) : ViewModel() {

    fun insert(pedidoEntity: PedidoEntity) = viewModelScope.launch {
        repo.savePedido(pedidoEntity)
    }

    fun insertImage(images: List<Image>) = viewModelScope.launch {
        repo.saveImage(images)
    }

}

class PedidoCreateViewModelFactory(private val repo: PedidoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PedidoRepository::class.java).newInstance(repo)
    }

}
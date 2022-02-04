package com.example.pedidoroomapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pedidoroomapp.data.model.User
import com.example.pedidoroomapp.domain.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }
}

class UserViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(userRepository)
    }

}
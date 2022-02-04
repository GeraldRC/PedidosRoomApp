package com.example.pedidoroomapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.pedidoroomapp.R
import com.example.pedidoroomapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
    }
}
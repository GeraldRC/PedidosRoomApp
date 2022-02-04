package com.example.pedidoroomapp.ui.options

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pedidoroomapp.R
import com.example.pedidoroomapp.databinding.FragmentPedidoOptionsBinding


class PedidoFragmentOptions : Fragment(R.layout.fragment_pedido_options) {

    private lateinit var binding: FragmentPedidoOptionsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPedidoOptionsBinding.bind(view)
        clickListeners()

    }


    private fun clickListeners(){
        binding.optCreate.setOnClickListener {
            findNavController().navigate(R.id.action_pedidoFragmentOptions_to_pedidoFragmentCreate)
        }

        binding.optPedidos.setOnClickListener {
            findNavController().navigate(R.id.action_pedidoFragmentOptions_to_pedidoFragment)
        }

    }
}
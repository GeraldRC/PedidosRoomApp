package com.example.pedidoroomapp.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.pedidoroomapp.R
import com.example.pedidoroomapp.databinding.FragmentPedidoSearchBinding

class PedidoSearchFragment : Fragment(R.layout.fragment_pedido_search) {

    private lateinit var binding: FragmentPedidoSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPedidoSearchBinding.bind(view)
    }

}
package com.example.pedidoroomapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidoroomapp.R
import com.example.pedidoroomapp.data.local.AppDataBase
import com.example.pedidoroomapp.data.model.PedidoWithImage
import com.example.pedidoroomapp.databinding.FragmentPedidoBinding
import com.example.pedidoroomapp.domain.PedidoRepositoryImpl
import com.example.pedidoroomapp.presentation.PedidoViewModel
import com.example.pedidoroomapp.presentation.PedidoViewModelFactory
import com.example.pedidoroomapp.ui.main.adapter.PedidoAdapter


class PedidoFragment : Fragment(R.layout.fragment_pedido), PedidoAdapter.OnPedidoClickListener {

    private lateinit var binding: FragmentPedidoBinding
    private lateinit var recyclerView: RecyclerView

    private val viewModel by viewModels<PedidoViewModel> {
        PedidoViewModelFactory(
            PedidoRepositoryImpl(
                AppDataBase.getDatabase(requireContext()).pedidoDao()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPedidoBinding.bind(view)

        recyclerView = binding.rvPed
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadingPed()
        binding.btnSearch.setOnClickListener {
            getPedido()
        }

    }

    private fun loadingPed() {
        viewModel.allPedido.observe(viewLifecycleOwner) { pedidos ->
            recyclerView.adapter = PedidoAdapter(pedidos, this@PedidoFragment)
        }
    }

    private fun getPedido() {
        val ped = binding.txtSearch.text.toString()
        if (ped.trim().isEmpty()) {
            loadingPed()
        } else {
            viewModel.getPedido(ped.toInt()).observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Pedido: $ped no Encontrado",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    recyclerView.adapter = PedidoAdapter(it, this@PedidoFragment)
                }
            }
        }
    }

    override fun onPedidoClick(pedidos: PedidoWithImage) {
        val action =
            PedidoFragmentDirections.actionPedidoFragmentToPedidoFragmentDetails(pedidos.pedidoEntity.numPed)
        findNavController().navigate(action)
    }


}
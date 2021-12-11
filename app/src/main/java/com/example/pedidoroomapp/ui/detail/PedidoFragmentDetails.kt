package com.example.pedidoroomapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pedidoroomapp.R
import com.example.pedidoroomapp.data.local.AppDataBase
import com.example.pedidoroomapp.data.model.PedidoEntity
import com.example.pedidoroomapp.databinding.FragmentPedidoDetailsBinding
import com.example.pedidoroomapp.domain.PedidoRepositoryImpl
import com.example.pedidoroomapp.presentation.PedidoDetailViewModel
import com.example.pedidoroomapp.presentation.PedidoDetailViewModelFactory
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.io.File


class PedidoFragmentDetails : Fragment(R.layout.fragment_pedido_details) {

    private lateinit var binding: FragmentPedidoDetailsBinding
    private val args by navArgs<PedidoFragmentDetailsArgs>()
    private var pedidoEntity: PedidoEntity? = null

    private val carouselItem = mutableListOf<CarouselItem>()

    private val viewModel by viewModels<PedidoDetailViewModel> {
        PedidoDetailViewModelFactory(
            PedidoRepositoryImpl(
                AppDataBase.getDatabase(requireContext()).pedidoDao()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPedidoDetailsBinding.bind(view)
        delete()

        viewModel.getPedido(args.pedido).observe(viewLifecycleOwner) { pedidos ->
            pedidos.forEach { pedido ->
                pedidoEntity = pedido.pedidoEntity
                binding.txtNumPed.text = "Pedido N° : ${pedido.pedidoEntity.numPed.toString()}"
                binding.txtCantPed.text = "Articulos Solicitados° : ${pedido.pedidoEntity.canArt}"
                binding.txtArtEnt.text = "Articulos Entregados° : ${pedido.pedidoEntity.cantArtEnt}"
                binding.txtDatePed.text = "Fecha : ${pedido.pedidoEntity.date}"
                binding.txtDetail.text = "${pedido.pedidoEntity.detail}"
                pedido.images.forEach {
                    carouselItem.add(CarouselItem(imageUrl = it.path))
                }
            }
            binding.carousel.setData(carouselItem)
        }

        binding.carousel.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                carouselItem.imageUrl?.let { openImage(it) }
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                // ...
            }
        }
    }

    private fun openImage(uri: String) {
        val galleryIntent: Intent = Intent(
            Intent.ACTION_VIEW,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        val uri1 = Uri.parse(uri)
        galleryIntent.setDataAndType(uri1, "image/**")
        galleryIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(galleryIntent)
    }

    private fun delete() {
        binding.fabDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Pedido")
                .setMessage(" Desea eliminar el pedido?")
                .setNegativeButton("No") { _, _ ->
                    Toast.makeText(requireContext(), "Anday Puro Weiando!!", Toast.LENGTH_LONG)
                        .show()
                }
                .setPositiveButton("Si") { _, _ ->
                    lifecycleScope.launch {
                        pedidoEntity?.let { it1 -> viewModel.delete(it1) }
                    }
                    Toast.makeText(requireContext(), "Pedido Eliminado!", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_pedidoFragmentDetails_to_pedidoFragment)
                }
            dialog.create()
            dialog.show()
        }
    }


}
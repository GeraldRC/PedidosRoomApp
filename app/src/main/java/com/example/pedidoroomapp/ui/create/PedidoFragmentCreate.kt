package com.example.pedidoroomapp.ui.create

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pedidoroomapp.R
import com.example.pedidoroomapp.data.local.AppDataBase
import com.example.pedidoroomapp.data.model.Image
import com.example.pedidoroomapp.data.model.PedidoEntity
import com.example.pedidoroomapp.databinding.FragmentPedidoCreateBinding
import com.example.pedidoroomapp.domain.PedidoRepositoryImpl
import com.example.pedidoroomapp.presentation.PedidoCreateViewModel
import com.example.pedidoroomapp.presentation.PedidoCreateViewModelFactory
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws


class PedidoFragmentCreate : Fragment(R.layout.fragment_pedido_create) {

    private lateinit var binding: FragmentPedidoCreateBinding
    private lateinit var currentPhotoPath: String
    private var latestTempUri: Uri? = null
    private var numPedtemp: Int = 0
    private var images: MutableList<Image> = arrayListOf()

    private val viewmodel by viewModels<PedidoCreateViewModel> {
        PedidoCreateViewModelFactory(
            PedidoRepositoryImpl(
                AppDataBase.getDatabase(requireContext()).pedidoDao()
            )
        )
    }

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSucces ->
            if (isSucces) {
                latestTempUri?.let { uri ->
                    images.add(Image(0, numPedtemp, uri.toString()))
                    binding.ivPhoto.setImageURI(uri)
                }
            } else {
                latestTempUri?.let {
                    Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPedidoCreateBinding.bind(view)
        setClickListeners()

    }

    private fun subirPedido() {
        val numPed = binding.txtNumped.text.toString()
        val canArt = binding.txtArt.text.toString()
        val canArtEnt = binding.txtArtEnt.text.toString()
        val detail = binding.txtDetail.text.toString()

        if (comprobarCampos(numPed, canArt, canArtEnt, detail)) {

            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Pedido")
                .setMessage("Crear Pedido $numPed ?")
                .setNegativeButton("No") { _, _ ->
                    Toast.makeText(requireContext(), "Revise Información", Toast.LENGTH_LONG).show()
                }
                .setPositiveButton("Si") { _, _ ->
                    insertPed(
                        PedidoEntity(
                            0,
                            numPed.toInt(),
                            canArt.toInt(),
                            canArtEnt.toInt(),
                            detail,
                            getDate(),
                        )
                    )
                    insertImage(images = images)
                    Toast.makeText(requireContext(), "Pedido $numPed Creado", Toast.LENGTH_LONG)
                        .show()
                    findNavController().navigate(R.id.action_pedidoFragmentCreate_to_pedidoFragmentOptions)

                }
            dialog.create()
            dialog.show()
        }
    }
    private fun setClickListeners() {
        binding.ivPhoto.setOnClickListener {
            val numPed = binding.txtNumped.text.toString()
            if (!checkNumPed()) {
                takePhoto(numPed.toInt())
            }
        }
        binding.floatingActionButton.setOnClickListener {
            subirPedido()
        }

    }

    private fun checkNumPed(): Boolean {
        var check = false
        val numPed = binding.txtNumped.text.toString()
        if (numPed.trim().isEmpty()) {
            binding.inputNumPed.error = "Ingrese Numero de Pedido"
            check = true
        }else{
            binding.inputNumPed.error = null
        }
        return check
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = getDate()
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun takePhoto(numPed: Int) {
        lifecycleScope.launchWhenCreated {
            getFileUri().let { uri ->
                latestTempUri = uri
                numPedtemp = numPed
                takeImageResult.launch(uri)
            }
        }
    }

    private fun getFileUri(): Uri {
        val tmpFile = createImageFile()
        return FileProvider.getUriForFile(
            requireContext(),
            "com.example.android.fileprovider",
            tmpFile
        )
    }

    private fun insertPed(pedido: PedidoEntity) {
        viewmodel.insert(pedido)
    }

    private fun insertImage(images: List<Image>) {
        viewmodel.insertImage(images)
    }

    private fun comprobarCampos(
        numped: String,
        cantArt: String,
        cantArtEnt: String,
        detail: String
    ): Boolean {

        var cont = 0

        if (numped.trim().isEmpty()) {
            binding.inputNumPed.error = "Ingrese Numero Pedido"
        } else {
            binding.inputNumPed.error = null
            cont += 1
        }

        if (cantArt.trim().isEmpty()) {
            binding.inputArt.error = "Ingrese Cantidad de Articulos"
        } else {
            binding.inputArt.error = null
            cont += 1
        }

        if (cantArtEnt.trim().isEmpty()) {
            binding.inputArtEnt.error = "Ingrese Cantidad de Articulos Entregados"
        } else {
            binding.inputArtEnt.error = null
            cont += 1
        }

        if (detail.trim().isEmpty()) {
            binding.inputDetail.error = "Ingrese Detalle"
        } else {
            binding.inputDetail.error = null
            cont += 1
        }
        return cont == 4

    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(): String {
        return SimpleDateFormat("EEEE MMMM d HH:mm:ss").format(Date())
    }


}



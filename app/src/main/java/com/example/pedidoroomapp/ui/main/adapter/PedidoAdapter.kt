package com.example.pedidoroomapp.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidoroomapp.core.BaseViewHolder
import com.example.pedidoroomapp.data.model.PedidoWithImage
import com.example.pedidoroomapp.databinding.ItemNewPedidoBinding


class PedidoAdapter(
    private val pedidosList: List<PedidoWithImage>,
    private val itemClickListener: OnPedidoClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnPedidoClickListener {
        fun onPedidoClick(pedidos: PedidoWithImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ItemNewPedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = PedidoViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf {
                it != DiffUtil.DiffResult.NO_POSITION
            } ?: return@setOnClickListener
            itemClickListener.onPedidoClick(pedidosList[position])
        }


        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PedidoViewHolder -> holder.render(pedidosList[position])
        }
    }

    override fun getItemCount(): Int = pedidosList.size


    private inner class PedidoViewHolder(val binding: ItemNewPedidoBinding, val context: Context) :
        BaseViewHolder<PedidoWithImage>(binding.root){
        @SuppressLint("SetTextI18n")
        override fun render(item: PedidoWithImage) {
            binding.txtPed.text = "Pedido: ${item.pedidoEntity.numPed}"
            binding.txtFecha.text= "Fecha: ${item.pedidoEntity.date}"
        }
    }

}

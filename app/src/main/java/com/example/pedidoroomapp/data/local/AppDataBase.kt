package com.example.pedidoroomapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pedidoroomapp.data.model.Image
import com.example.pedidoroomapp.data.model.PedidoEntity

@Database(entities = [PedidoEntity::class,Image::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun pedidoDao(): PedidoDao

    companion object{

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "pedidos"
            ).build()
            return INSTANCE!!
        }

    }

}
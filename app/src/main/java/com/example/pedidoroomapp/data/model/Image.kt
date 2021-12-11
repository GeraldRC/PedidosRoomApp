package com.example.pedidoroomapp.data.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class Image(

    @PrimaryKey(autoGenerate = true)
    val idImage: Int,

    @ColumnInfo(name = "numPed")
    val numPed: Int,

    @ColumnInfo(name = "path")
    val path: String

)

package com.example.denunciasecuador.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

// Usamos la anotación @Entity para que Room pueda crear una tabla a partir de esta clase.
// En model/Denuncia.kt
@Entity(tableName = "denuncias")
data class Denuncia(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val descripcion: String,
    val fecha: Long,
    val ciudad: String,
    val tipo: String,
    val esPublica: Boolean,
    val estado: String = "Pendiente"
)


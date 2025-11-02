package com.example.denunciasecuador.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.denunciasecuador.model.Denuncia

@Dao
interface DenunciaDao {
    @Insert
    suspend fun insertar(denuncia: Denuncia)

    // ... (otras funciones como obtenerDenunciasPublicas)

    // ✅ NUEVA FUNCIÓN AÑADIDA
    // Obtiene TODAS las denuncias, ordenadas por fecha más reciente.
    // En una app real, aquí añadirías un "WHERE userId = :userId".
    @Query("SELECT * FROM denuncias ORDER BY fecha DESC")
    suspend fun obtenerTodasMisDenuncias(): List<Denuncia>

    //                       👇 CORREGIDO
    @Query("SELECT * FROM denuncias WHERE esPublica = 1 AND fecha >= :fechaInicio ORDER BY fecha DESC")
    suspend fun obtenerDenunciasPublicas(fechaInicio: Long): List<Denuncia>

    //                       👇 CORREGIDO
    @Query("SELECT * FROM denuncias WHERE esPublica = 1")
    suspend fun obtenerTodasPublicas(): List<Denuncia>

    //                       👇 CORREGIDO
    @Query("SELECT * FROM denuncias WHERE esPublica = 1 AND fecha >= :fechaLimite")
    suspend fun obtenerPublicasDesde(fechaLimite: Long): List<Denuncia>
}


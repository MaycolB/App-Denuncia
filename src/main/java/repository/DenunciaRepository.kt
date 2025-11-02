package com.example.denunciasecuador.repository

import com.example.denunciasecuador.model.Denuncia

// ✅ ESTA ES LA CLASE QUE FALTA EN TU PROYECTO
// El repositorio necesita el DAO para poder acceder a la base de datos.
// Se lo pasamos en el constructor.
class DenunciaRepository(private val denunciaDao: DenunciaDao) {

    /**
     * Inserta una nueva denuncia en la base de datos.
     * Esta es una función 'suspend' porque las operaciones de base de datos
     * deben ejecutarse en un hilo secundario para no bloquear la UI.
     */
    suspend fun insertarDenuncia(denuncia: Denuncia) {
        denunciaDao.insertar(denuncia)
    }

    /**
     * Obtiene TODAS las denuncias de la base de datos.
     * Usado para la pantalla "Mis Denuncias".
     */
    suspend fun obtenerTodasMisDenuncias(): List<Denuncia> {
        return denunciaDao.obtenerTodasMisDenuncias()
    }

    /**
     * Obtiene solo las denuncias PÚBLICAS desde una fecha específica.
     * Usado para la pantalla "Consultar Denuncias Públicas".
     */
    suspend fun obtenerPublicasDesde(fechaDesdeMillis: Long): List<Denuncia> {
        return denunciaDao.obtenerPublicasDesde(fechaDesdeMillis)
    }

    // Aquí podrías añadir más funciones en el futuro, como:
    // suspend fun borrarDenuncia(denuncia: Denuncia) { ... }
    // suspend fun actualizarDenuncia(denuncia: Denuncia) { ... }
}

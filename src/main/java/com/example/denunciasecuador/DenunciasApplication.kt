package com.example.denunciasecuador

import android.app.Application
import androidx.room.Room
import com.example.denunciasecuador.repository.AppDatabase
import com.example.denunciasecuador.repository.DenunciaRepository

// ✅ Esta es la clase que tu ViewModel está buscando.
class DenunciasApplication : Application() {

    // Usamos 'lazy' para que la base de datos y el repositorio se creen solo
    // la primera vez que se necesiten, y no cada vez.
    private val database by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "denuncias_database" // Nombre del archivo de la base de datos
        )
            // Esto es útil para desarrollo: si cambias el esquema, la DB se recrea.
            .fallbackToDestructiveMigration()
            .build()
    }

    // Creamos una única instancia del repositorio para toda la app.
    // Le pasamos el DAO de la base de datos que acabamos de crear.
    val repository by lazy {
        DenunciaRepository(database.denunciaDao())
    }
}

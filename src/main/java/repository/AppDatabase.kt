package com.example.denunciasecuador.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.denunciasecuador.model.Denuncia

@Database(entities = [Denuncia::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun denunciaDao(): DenunciaDao
}

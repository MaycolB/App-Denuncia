package com.example.denunciasecuador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import navigation.AppNavigation
import com.example.denunciasecuador.ui.theme.DenunciasEcuadorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DenunciasEcuadorTheme {
                // Carga la navegación principal de la app
                AppNavigation()
            }
        }
    }
}

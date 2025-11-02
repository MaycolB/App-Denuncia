// C:/Users/DELL/AndroidStudioProjects/DenunciasEcuador/app/src/main/java/com/example/denunciasecuador/ui/ManualScreen.kt

package com.example.denunciasecuador.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualScreen(navController: NavHostController) {
    // Scaffold proporciona la estructura básica de la pantalla (barra superior, contenido, etc.)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manual de Usuario") },
                // Icono para volver a la pantalla anterior
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        // Column con scroll para ver todo el contenido si no cabe en la pantalla
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // --- Encabezado ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("DenunciasEcuador", style = MaterialTheme.typography.headlineMedium)
                    Text(
                        "Universidad ECOTEC - Programación en Dispositivos Móviles",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                    Text("Profesor: Ing. Christian Merchán Millán", style = MaterialTheme.typography.bodySmall)
                    Text("Año: 2025", style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Secciones del Manual ---
            ManualSection("1. Descripción General", "DenunciasEcuador permite registrar denuncias de Aseo y Ornato, Tránsito Vial y Delitos. Las denuncias pueden ser públicas o privadas y se almacenan localmente con Room.")
            ManualSection("2. Requisitos del Sistema", "Android 8.0 o superior, Android Studio Koala, Kotlin, Jetpack Compose, Room, Navigation Compose.")
            ManualSection("3. Instalación", "Abrir el proyecto en Android Studio, sincronizar Gradle y ejecutar en un emulador o dispositivo físico.")
            ManualSection("4. Interfaz Principal", "LoginScreen: para el ingreso de usuario y clave. FormularioDenunciaScreen: para el ingreso de los datos de la denuncia.")
            ManualSection("5. Funcionamiento Técnico", "La aplicación utiliza una arquitectura moderna con Room para la base de datos local, ViewModel para gestionar la lógica de la UI, corrutinas para operaciones asíncronas y Navigation Compose para el flujo entre pantallas.")
            ManualSection("6. Flujo del Usuario", "1. Abrir la app.\n2. Ingresar usuario y contraseña en la pantalla de Login.\n3. Rellenar y enviar el formulario de denuncia.\n4. Recibir confirmación en pantalla.")
            ManualSection("7. Mensajes de Validación", "La app guía al usuario con mensajes como: 'Por favor ingrese usuario y contraseña' o 'Denuncia guardada correctamente'.")
            ManualSection("8. Mejoras Futuras", "Implementar consulta de denuncias públicas, autenticación real con backend, capacidad para adjuntar fotos/videos y sincronización con un servidor en la nube.")
            ManualSection("9. Créditos", "Desarrollador: Leandro Alberto Suarez Zambrano\nInstitución: Universidad ECOTEC.")

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Composable reutilizable para mostrar una sección del manual.
 * @param title El título de la sección.
 * @param content El contenido descriptivo de la sección.
 */
@Composable
fun ManualSection(title: String, content: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

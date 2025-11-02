package com.example.denunciasecuador.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.denunciasecuador.model.Denuncia // Correcto: usando el modelo unificado
import com.example.denunciasecuador.viewmodel.DenunciaViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable

fun ConsultaDenunciasScreen(viewModel: DenunciaViewModel = viewModel(factory = DenunciaViewModel.Factory)) {
    // Observa el estado correcto desde el ViewModel
    val uiState by viewModel.consultaState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Consultar Denuncias Públicas", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // ✅ 1. AÑADIDO: Botones para iniciar la búsqueda
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val ayer = System.currentTimeMillis() - 24 * 60 * 60 * 1000
                viewModel.obtenerDenunciasPublicasDesde(ayer)
            }) {
                Text("Último día")
            }

            Button(onClick = {
                val semana = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
                viewModel.obtenerDenunciasPublicasDesde(semana)
            }) {
                Text("Última semana")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ 2. AÑADIDO: Lógica completa para mostrar carga, error o la lista
        when {
            // Estado de Carga
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            // Estado de Error
            uiState.error != null -> {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            // Estado con Lista Vacía
            uiState.denuncias.isEmpty() -> {
                Text(
                    text = "No hay denuncias para el filtro seleccionado. Inicia una búsqueda.",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            // Estado con Contenido
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.denuncias) { denuncia ->
                        DenunciaCard(denuncia) // Llamada al Composable de la tarjeta
                    }
                }
            }
        }
    }
}

// ✅ 3. AÑADIDO: Composable para mostrar cada denuncia individualmente
@Composable
fun DenunciaCard(denuncia: Denuncia) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = denuncia.titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = denuncia.descripcion, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Ciudad: ${denuncia.ciudad}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Tipo: ${denuncia.tipo}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Estado: ${denuncia.estado}", style = MaterialTheme.typography.bodySmall)
            val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(denuncia.fecha))
            Text(text = "Fecha: $fecha", style = MaterialTheme.typography.bodySmall)
        }
    }
}

package com.example.denunciasecuador.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.denunciasecuador.model.Denuncia
import com.example.denunciasecuador.viewmodel.DenunciaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisDenunciasScreen(viewModel: DenunciaViewModel = viewModel(factory = DenunciaViewModel.Factory)) {
    // Usamos LaunchedEffect para que las denuncias se carguen una sola vez
    // cuando la pantalla aparece por primera vez.
    LaunchedEffect(Unit) {
        viewModel.cargarMisDenuncias()
    }

    val uiState by viewModel.misDenunciasState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Denuncias") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                uiState.denuncias.isEmpty() -> {
                    Text(
                        text = "Aún no has registrado ninguna denuncia.",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(uiState.denuncias) { denuncia ->
                            MiDenunciaCard(denuncia) // Usamos una tarjeta con más detalles
                        }
                    }
                }
            }
        }
    }
}

// Una tarjeta un poco más detallada para esta pantalla
@Composable
fun MiDenunciaCard(denuncia: Denuncia) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Sombra sutil
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant, // Un color de fondo ligeramente diferente
        )) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(denuncia.titulo, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text(denuncia.descripcion,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant // Color más suave para el texto secundario
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Fila para metadatos (Fecha, Ciudad)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val fecha = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(denuncia.fecha))
                Text(
                    text = "Reportado el: $fecha",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = denuncia.ciudad,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Fila para Estado y Visibilidad
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Estado de la denuncia
                Text(
                    text = "Estado: ${denuncia.estado}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = when(denuncia.estado){
                        "Pendiente" -> Color.Gray
                        "En revisión" -> Color(0xFFFFA500) // Naranja
                        "Resuelto" -> Color(0xFF388E3C) // Verde oscuro
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )

                // Visibilidad (Pública o Privada)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (denuncia.esPublica) Icons.Filled.Public else Icons.Default.Lock,
                        contentDescription = "Visibilidad",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        if (denuncia.esPublica) "Pública" else "Privada",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

            }
            Spacer(Modifier.height(4.dp))
            val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(denuncia.fecha))
            Text("Reportado el: $fecha", style = MaterialTheme.typography.bodySmall)
        }
    }
}

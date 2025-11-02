// Archivo: DenunciaScreen.kt

// ... (otras importaciones)
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// 👇 ¡ESTA ES LA LÍNEA QUE DEBES CAMBIAR!
import com.example.denunciasecuador.model.Denuncia // Apunta al paquete 'model'
import com.example.denunciasecuador.viewmodel.DenunciaViewModel

// ... (resto del código de AppNavigation y DenunciaScreen)

@Composable
fun DenunciaScreen(viewModel: DenunciaViewModel) {
    // ... tu código actual, no necesita cambios ...
    val uiState by viewModel.consultaState.collectAsState() // Asumo que quieres usar consultaState aquí

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Aquí usas la lista de denuncias del estado
                items(uiState.denuncias) { denuncia ->
                    DenunciaItem(denuncia = denuncia)
                }
            }
        }
    }
}

// Este Composable ahora encontrará la clase 'Denuncia' sin problemas
@Composable
fun DenunciaItem(denuncia: Denuncia) {
    Column {
        Text(text = denuncia.titulo, style = MaterialTheme.typography.titleMedium)
        Text(text = denuncia.descripcion, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = "Estado: ${denuncia.estado}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

annotation class DenunciaScreen

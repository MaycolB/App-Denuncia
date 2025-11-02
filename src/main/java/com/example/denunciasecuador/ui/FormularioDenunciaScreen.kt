package com.example.denunciasecuador.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.denunciasecuador.viewmodel.DenunciaViewModel
import com.example.denunciasecuador.viewmodel.FormularioEvent;
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioDenunciaScreen(navController: NavHostController, viewModel: DenunciaViewModel = viewModel(factory = DenunciaViewModel.Factory)) {

    val context = LocalContext.current
    val formState by viewModel.formularioState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = formState.guardadoConExito) {
        if (formState.guardadoConExito) {
            Toast.makeText(context, "Denuncia guardada correctamente", Toast.LENGTH_SHORT).show()
            viewModel.onFormularioEvent(FormularioEvent.OnResetGuardado)
            navController.navigate("consulta")
        }
    }

    if (showDatePicker) {
        // 1. Creamos el estado del DatePicker aquí para poder acceder a él en el botón.
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(
                    onClick = {
                        // 2. Al hacer clic en "Aceptar", obtenemos la fecha del estado...
                        datePickerState.selectedDateMillis?.let {
                            // ...y la enviamos al ViewModel.
                            viewModel.onFormularioEvent(FormularioEvent.OnFechaChange(it))
                        }
                        showDatePicker = false // ...y cerramos el diálogo.
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            // 3. El DatePicker ahora es mucho más simple. Solo necesita el estado.
            DatePicker(state = datePickerState)
        }
    }

    val fechaFormateada = remember(formState.fecha) {
        formState.fecha?.let {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            sdf.format(Date(it))
        } ?: ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // --- (El resto del código de la UI es idéntico y no necesita cambios) ---
        Text("Registrar Nueva Denuncia", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = formState.titulo,
            onValueChange = { viewModel.onFormularioEvent(FormularioEvent.OnTituloChange(it)) },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = formState.descripcion,
            onValueChange = { viewModel.onFormularioEvent(FormularioEvent.OnDescripcionChange(it)) },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = fechaFormateada,
            onValueChange = {},
            label = { Text("Fecha del evento") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formState.ciudad,
            onValueChange = { viewModel.onFormularioEvent(FormularioEvent.OnCiudadChange(it)) },
            label = { Text("Ciudad / Provincia")  },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = formState.tipoSeleccionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de denuncia") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DenunciaViewModel.tiposDeDenuncia.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo) },
                        onClick = {
                            viewModel.onFormularioEvent(FormularioEvent.OnTipoChange(tipo))
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("¿Hacer esta denuncia pública?", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = formState.esPublica,
                onCheckedChange = { viewModel.onFormularioEvent(FormularioEvent.OnEsPublicaChange(it)) }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (!viewModel.guardarDenuncia()) {
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Enviar denuncia")
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre botones

        // ✅ BOTÓN AÑADIDO PARA NAVEGAR A "MIS DENUNCIAS"
        OutlinedButton(
            onClick = { navController.navigate("mis_denuncias") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultar mis denuncias")
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espacio adicional

        OutlinedButton(
            onClick = {
                // Navega a la ruta del manual que definiste en AppNavigation.kt
                navController.navigate("manual_screen_route")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Manual de Usuario")
        }
    }

    }

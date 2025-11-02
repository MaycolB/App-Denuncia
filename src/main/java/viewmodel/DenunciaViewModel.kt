package com.example.denunciasecuador.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.denunciasecuador.DenunciasApplication
import com.example.denunciasecuador.model.Denuncia
import com.example.denunciasecuador.repository.DenunciaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// --- ESTADOS DE LA UI ---

// Estado para el formulario de nueva denuncia
data class FormularioUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: Long? = null,
    val ciudad: String = "",
    val tipoSeleccionado: String = "Aseo y Ornato",
    val esPublica: Boolean = true,
    val guardadoConExito: Boolean = false
)

// Estado para las pantallas de consulta de denuncias
data class ConsultaUiState(
    val denuncias: List<Denuncia> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// --- VIEWMODEL ---

// ✅ 1. El ViewModel AHORA PIDE el repositorio en su constructor.
class DenunciaViewModel(private val repository: DenunciaRepository) : ViewModel() {

    // --- Estados para cada pantalla ---
    private val _formularioState = MutableStateFlow(FormularioUiState())
    val formularioState = _formularioState.asStateFlow()

    private val _consultaState = MutableStateFlow(ConsultaUiState())
    val consultaState = _consultaState.asStateFlow()

    private val _misDenunciasState = MutableStateFlow(ConsultaUiState())
    val misDenunciasState = _misDenunciasState.asStateFlow()


    // --- Lógica del Formulario ---
    fun onFormularioEvent(event: FormularioEvent) {
        when (event) {
            is FormularioEvent.OnTituloChange -> _formularioState.update { it.copy(titulo = event.titulo) }
            is FormularioEvent.OnDescripcionChange -> _formularioState.update { it.copy(descripcion = event.descripcion) }
            is FormularioEvent.OnFechaChange -> _formularioState.update { it.copy(fecha = event.fecha) }
            is FormularioEvent.OnCiudadChange -> _formularioState.update { it.copy(ciudad = event.ciudad) }
            is FormularioEvent.OnTipoChange -> _formularioState.update { it.copy(tipoSeleccionado = event.tipo) }
            is FormularioEvent.OnEsPublicaChange -> _formularioState.update { it.copy(esPublica = event.esPublica) }
            FormularioEvent.OnResetGuardado -> _formularioState.update { it.copy(guardadoConExito = false) }
        }
    }

    fun guardarDenuncia(): Boolean {
        val formState = _formularioState.value
        if (formState.titulo.isBlank() || formState.descripcion.isBlank() || formState.fecha == null || formState.ciudad.isBlank()) {
            return false
        }

        val nuevaDenuncia = Denuncia(
            titulo = formState.titulo,
            descripcion = formState.descripcion,
            fecha = formState.fecha!!,
            ciudad = formState.ciudad,
            tipo = formState.tipoSeleccionado,
            esPublica = formState.esPublica
        )

        viewModelScope.launch {
            // ✅ 2. AHORA SÍ GUARDAMOS EN LA BASE DE DATOS REAL
            repository.insertarDenuncia(nuevaDenuncia)
            // Limpia el formulario y notifica que se guardó
            _formularioState.value = FormularioUiState(guardadoConExito = true)
        }
        return true
    }

    // --- Lógica de Consulta Pública ---
    fun obtenerDenunciasPublicasDesde(fechaDesdeMillis: Long) {
        viewModelScope.launch {
            _consultaState.update { it.copy(isLoading = true, error = null) }
            try {
                // ✅ 3. AHORA SÍ LEEMOS DESDE LA BASE DE DATOS REAL
                val denunciasPublicas = repository.obtenerPublicasDesde(fechaDesdeMillis)
                _consultaState.update { it.copy(denuncias = denunciasPublicas, isLoading = false) }
            } catch (e: Exception) {
                _consultaState.update { it.copy(isLoading = false, error = "Error al obtener denuncias públicas: ${e.message}") }
            }
        }
    }

    // --- Lógica de "Mis Denuncias" ---
    fun cargarMisDenuncias() {
        viewModelScope.launch {
            _misDenunciasState.update { it.copy(isLoading = true, error = null) }
            try {
                // ✅ 4. AHORA SÍ LEEMOS DESDE LA BASE DE DATOS REAL
                val misDenuncias = repository.obtenerTodasMisDenuncias()
                _misDenunciasState.update { it.copy(denuncias = misDenuncias, isLoading = false) }
            } catch (e: Exception) {
                _misDenunciasState.update { it.copy(isLoading = false, error = "Error al cargar mis denuncias: ${e.message}") }
            }
        }
    }

    // ✅ 5. El Factory es CRUCIAL para poder crear el ViewModel con el repositorio.
    //    Debe estar DENTRO de la clase.
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Obtenemos la aplicación y su repositorio
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as DenunciasApplication
                val repository = application.repository

                // Creamos y retornamos la instancia del ViewModel
                return DenunciaViewModel(repository) as T
            }
        }
        val tiposDeDenuncia = listOf("Aseo y Ornato", "Tránsito Vial", "Delito", "Otro")
    }
}

// ✅ La clase sellada está FUERA de la clase ViewModel, lo cual es correcto.
sealed class FormularioEvent {
    data class OnTituloChange(val titulo: String) : FormularioEvent()
    data class OnDescripcionChange(val descripcion: String) : FormularioEvent()
    data class OnFechaChange(val fecha: Long) : FormularioEvent()
    data class OnCiudadChange(val ciudad: String) : FormularioEvent()
    data class OnTipoChange(val tipo: String) : FormularioEvent()
    data class OnEsPublicaChange(val esPublica: Boolean) : FormularioEvent()
    object OnResetGuardado : FormularioEvent()
}
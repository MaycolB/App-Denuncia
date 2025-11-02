package com.example.denunciasecuador.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.denunciasecuador.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.navigation.NavController

@Composable
fun AppLogo(width: Dp, height: Dp) {
    Image(
        painter = painterResource(id = R.drawable.logo), // Reemplaza 'ic_launcher_foreground' con el nombre de tu logo
        contentDescription = "Logo de la aplicación",
        modifier = Modifier
            .width(width)
            .height(height)
    )
}
@Composable
fun LoginScreen(navController: NavController, onLoginSuccess: () -> Unit) {
    var usuario by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- LOGO DE LA APP ---
        // Asumiendo que tienes un composable AppLogo como discutimos antes
        AppLogo(width = 150.dp, height = 150.dp)

        Spacer(modifier = Modifier.height(16.dp))

        // --- TÍTULO DE BIENVENIDA ---
        Text(
            text = "Bienvenido a Denuncias Ecuador",
            style = MaterialTheme.typography.headlineMedium, // Título más grande
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Inicia sesión para continuar",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant // Un color más suave
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- CAMPO DE USUARIO ---
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Correo electrónico o usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true // Asegura que sea una sola línea
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = clave,
            onValueChange = { clave = it },
            label = { Text("Clave") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password) // Muestra teclado de contraseña
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (usuario.isNotEmpty() && clave.isNotEmpty()) {
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }

        Spacer(modifier = Modifier.height(8.dp)) // Añadimos un espacio

        // ✅ BOTÓN DEL MANUAL

        // Botón para abrir el manual
        OutlinedButton(
            onClick = {
                navController.navigate("manual_screen_route")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Manual de Usuario")
        }
    }
}





package navigation

// Asegúrate de importar todas tus pantallas
import DenunciaScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.denunciasecuador.ui.ConsultaDenunciasScreen
import com.example.denunciasecuador.ui.FormularioDenunciaScreen
import com.example.denunciasecuador.ui.LoginScreen
import com.example.denunciasecuador.ui.ManualScreen
import com.example.denunciasecuador.ui.MisDenunciasScreen
import com.example.denunciasecuador.ui.MainScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_screen_route") {

        composable("login_screen_route") {
            // ✅ CORRECCIÓN 2: Pasa el navController a LoginScreen.
            LoginScreen(
                navController = navController, // <--- ¡AQUÍ ESTÁ LA CORRECCIÓN!
                onLoginSuccess = {
                    // Después del login, navegas al formulario.
                    navController.navigate("formulario") {
                        // Limpia el stack para que el usuario no pueda volver a la pantalla de login.
                        popUpTo("login_screen_route") { inclusive = true }
                    }
                }
            )
        }
        composable("main") {
            MainScreen()
        }

        composable("formulario") {
            // Se le pasa el navController para que el formulario pueda navegar
            // después de guardar.
            FormularioDenunciaScreen(navController = navController)
        }

        composable("consulta") {
            // Esta pantalla obtiene su ViewModel internamente, no necesita parámetros.
            ConsultaDenunciasScreen()
        }

        composable("denuncia") {
            // Esta pantalla obtiene su ViewModel internamente, no necesita parámetros.
            DenunciaScreen()
        }

        composable("manual_screen_route") {
            ManualScreen(navController = navController)
        }
        composable("mis_denuncias") {
            MisDenunciasScreen()
        }
    }
}


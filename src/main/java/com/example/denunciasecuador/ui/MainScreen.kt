package com.example.denunciasecuador.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Formulario : BottomNavItem("formulario", Icons.Default.AddCircle, "Registrar")
    object MisDenuncias : BottomNavItem("mis_denuncias", Icons.AutoMirrored.Filled.List, "Mis Denuncias")
    object ConsultaPublica : BottomNavItem("consulta", Icons.Default.Search, "Consultar")
    object Manual : BottomNavItem("manual", Icons.AutoMirrored.Filled.Help, "Manual")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Formulario.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Formulario.route) {
                FormularioDenunciaScreen(navController = navController)
            }
            composable(BottomNavItem.MisDenuncias.route) {
                MisDenunciasScreen()
            }
            composable(BottomNavItem.ConsultaPublica.route) {
                ConsultaDenunciasScreen()
            }
            composable(BottomNavItem.Manual.route) {
                ManualScreen(navController = navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Formulario,
        BottomNavItem.MisDenuncias,
        BottomNavItem.ConsultaPublica,
        BottomNavItem.Manual

    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

package com.pokedex.academy.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pokedex.academy.core.navigation.Screen
import com.pokedex.academy.core.theme.RedPrimary
import com.pokedex.academy.presentation.exercise.ExerciseScreen
import com.pokedex.academy.presentation.exercise.detail.ExerciseDetailScreen
import com.pokedex.academy.presentation.gen1_livedata.Gen1ListScreen
import com.pokedex.academy.presentation.gen1_livedata.detail.Gen1DetailScreen
import com.pokedex.academy.presentation.gen2_flow.Gen2ListScreen
import com.pokedex.academy.presentation.gen2_flow.detail.Gen2DetailScreen

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        title = "Gen 1 (LiveData)",
        route = Screen.Gen1List.route,
        icon = Icons.Default.AutoStories
    ),
    BottomNavItem(
        title = "Gen 2 (Flow)",
        route = Screen.Gen2List.route,
        icon = Icons.Default.Bolt
    ),
    BottomNavItem(
        title = "Taller Alumnos",
        route = Screen.ExerciseList.route,
        icon = Icons.Default.School
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Ocultar barra inferior en pantallas de detalle
    val showBottomBar = currentRoute in listOf(
        Screen.Gen1List.route,
        Screen.Gen2List.route,
        Screen.ExerciseList.route
    )

    Scaffold(
        topBar = {
            if (showBottomBar) {
                TopAppBar(
                    title = { Text("PokeDex Academy") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = RedPrimary,
                        titleContentColor = Color.White
                    )
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                            label = { Text(item.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Gen1List.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Módulo 1: Gen 1 (LiveData)
            composable(Screen.Gen1List.route) {
                Gen1ListScreen(
                    onPokemonClick = { pokemonId ->
                        navController.navigate(Screen.Gen1Detail.createRoute(pokemonId))
                    }
                )
            }
            composable(
                route = Screen.Gen1Detail.route,
                arguments = listOf(navArgument("pokemonId") { type = NavType.StringType })
            ) {
                Gen1DetailScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Módulo 2: Gen 2 (StateFlow + SharedFlow)
            composable(Screen.Gen2List.route) {
                Gen2ListScreen(
                    onPokemonClick = { pokemonId ->
                        navController.navigate(Screen.Gen2Detail.createRoute(pokemonId))
                    }
                )
            }
            composable(
                route = Screen.Gen2Detail.route,
                arguments = listOf(navArgument("pokemonId") { type = NavType.StringType })
            ) {
                Gen2DetailScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Módulo 3: Taller Alumnos (Gen 3)
            composable(Screen.ExerciseList.route) {
                ExerciseScreen(
                    onPokemonClick = { pokemonId ->
                        navController.navigate(Screen.ExerciseDetail.createRoute(pokemonId))
                    }
                )
            }
            composable(
                route = Screen.ExerciseDetail.route,
                arguments = listOf(navArgument("pokemonId") { type = NavType.StringType })
            ) {
                ExerciseDetailScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

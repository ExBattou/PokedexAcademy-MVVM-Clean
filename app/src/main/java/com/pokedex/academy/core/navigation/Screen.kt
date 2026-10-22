package com.pokedex.academy.core.navigation

/**
 * Rutas de navegación para la aplicación PokeDex Academy.
 * Divide los tres módulos pedagógicos:
 * 1. Gen 1: Implementación con LiveData
 * 2. Gen 2: Implementación con Flow (StateFlow + SharedFlow)
 * 3. Taller: Ejercicio para alumnos
 */
sealed class Screen(val route: String) {
    // Gen 1 - LiveData
    data object Gen1List : Screen("gen1_list")
    data object Gen1Detail : Screen("gen1_detail/{pokemonId}") {
        fun createRoute(pokemonId: Int): String = "gen1_detail/$pokemonId"
    }

    // Gen 2 - Flow / SharedFlow
    data object Gen2List : Screen("gen2_list")
    data object Gen2Detail : Screen("gen2_detail/{pokemonId}") {
        fun createRoute(pokemonId: Int): String = "gen2_detail/$pokemonId"
    }

    // Módulo Taller / Alumnos (Gen 3)
    data object ExerciseList : Screen("exercise_list")
    data object ExerciseDetail : Screen("exercise_detail/{pokemonId}") {
        fun createRoute(pokemonId: Int): String = "exercise_detail/$pokemonId"
    }
}

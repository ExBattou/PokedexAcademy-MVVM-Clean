package com.pokedex.academy.presentation.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.core.theme.OrangeExercise
import com.pokedex.academy.presentation.common.components.*

/**
 * PANTALLA TALLER PRÁCTICO (GEN 3 - HOENN)
 *
 * El endpoint de PokeAPI y los Use Cases están 100% listos.
 * Esta pantalla consume 'viewModel.exerciseState'. Cuando el alumno completa
 * el ViewModel, los 135 Pokémon de Hoenn aparecerán automáticamente aquí.
 */
@Composable
fun ExerciseScreen(
    onPokemonClick: (Int) -> Unit,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    // Si el alumno usó StateFlow:
    val uiState by viewModel.exerciseState.collectAsStateWithLifecycle()

    // NOTA DIDÁCTICA: Si el alumno decidió usar LiveData en ExerciseViewModel,
    // en Compose simplemente reemplaza la línea anterior por:
    // val uiState by viewModel.exerciseState.observeAsState(initial = UiState.Idle)

    Column(modifier = Modifier.fillMaxSize()) {
        PedagogicalBanner(
            title = "Taller Práctico: Gen 3 (Hoenn)",
            badgeText = "Ejercicio Alumnos",
            badgeColor = OrangeExercise,
            description = "El endpoint y el UseCase ya existen. Abre 'ExerciseViewModel.kt' y resuelve los TODOs para conectar LiveData o StateFlow."
        )

        when (val state = uiState) {
            is UiState.Idle, is UiState.Loading -> {
                LoadingStateView(message = "Esperando la implementación reactiva del alumno...")
            }
            is UiState.Error -> {
                ErrorStateView(
                    message = state.message,
                    onRetry = { viewModel.loadGen3Pokemon() }
                )
            }
            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.data, key = { it.id }) { pokemon ->
                        PokemonCard(
                            pokemon = pokemon,
                            onClick = { onPokemonClick(pokemon.id) }
                        )
                    }
                }
            }
        }
    }
}

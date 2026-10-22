package com.pokedex.academy.presentation.gen1_livedata

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.core.theme.BlueLiveData
import com.pokedex.academy.presentation.common.components.*

/**
 * PANTALLA GEN 1 (KANTO) - Observada mediante LiveData
 *
 * NOTA DIDÁCTICA PARA LA CLASE:
 * Para observar un LiveData en Jetpack Compose, usamos la función de extensión:
 *    val state by viewModel.uiState.observeAsState(initial = UiState.Idle)
 * Esta función:
 * 1. Se suscribe al LiveData internamente usando el LifecycleOwner del Composable.
 * 2. Transforma cada emisión en un Compose `State<T>`.
 * 3. Dispara una recomposición automática de la UI cuando el valor cambia.
 */
@Composable
fun Gen1ListScreen(
    onPokemonClick: (Int) -> Unit,
    viewModel: Gen1ListViewModel = hiltViewModel()
) {
    // Suscripción al LiveData convirtiéndolo en State de Compose
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Idle)

    Column(modifier = Modifier.fillMaxSize()) {
        PedagogicalBanner(
            title = "1ra Generación: Kanto (1-151)",
            badgeText = "LiveData Architecture",
            badgeColor = BlueLiveData,
            description = "Estado administrado con MutableLiveData y consumido en Compose con observeAsState(). Patrón clásico de Jetpack MVVM."
        )

        when (val state = uiState) {
            is UiState.Idle, is UiState.Loading -> {
                LoadingStateView(message = "Consultando 1ra Generación con LiveData...")
            }
            is UiState.Error -> {
                ErrorStateView(
                    message = state.message,
                    onRetry = { viewModel.loadGen1Pokemon() }
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

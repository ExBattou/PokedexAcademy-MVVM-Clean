package com.pokedex.academy.presentation.gen2_flow

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.core.theme.GreenFlow
import com.pokedex.academy.presentation.common.components.*
import kotlinx.coroutines.flow.collectLatest

/**
 * PANTALLA GEN 2 (JOHTO) - Observada mediante StateFlow + SharedFlow
 *
 * NOTA DIDÁCTICA PARA LA CLASE:
 * 1. Para StateFlow en Compose usamos:
 *       val uiState by viewModel.uiState.collectAsStateWithLifecycle()
 *    ¿Por qué 'collectAsStateWithLifecycle' y NO 'collectAsState'?
 *    Porque 'collectAsStateWithLifecycle' cancela la recolección cuando la app pasa a segundo plano
 *    (Lifecycle.State.STARTED), ahorrando batería y ciclos de CPU.
 *
 * 2. Para SharedFlow (Eventos One-Shot) usamos un LaunchedEffect:
 *       LaunchedEffect(Unit) {
 *           viewModel.effectFlow.collectLatest { effect -> ... }
 *       }
 *    Esto garantiza que el evento se procesa sólo cuando llega y no sobrevive a recomposiciones.
 */
@Composable
fun Gen2ListScreen(
    onPokemonClick: (Int) -> Unit,
    viewModel: Gen2ListViewModel = hiltViewModel()
) {
    // 1. Recolección consciente del ciclo de vida para StateFlow
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    // 2. Recolección de eventos únicos efímeros (SharedFlow)
    LaunchedEffect(Unit) {
        viewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is Gen2UiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PedagogicalBanner(
                title = "2da Generación: Johto (152-251)",
                badgeText = "StateFlow + SharedFlow",
                badgeColor = GreenFlow,
                description = "Estado con StateFlow (collectAsStateWithLifecycle) y eventos efímeros con SharedFlow. El estándar actual recomendado por Google."
            )

            when (val state = uiState) {
                is UiState.Idle, is UiState.Loading -> {
                    LoadingStateView(message = "Recolectando StateFlow de 2da Generación...")
                }
                is UiState.Error -> {
                    ErrorStateView(
                        message = state.message,
                        onRetry = { viewModel.loadGen2Pokemon() }
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
                                onClick = {
                                    viewModel.onPokemonAction(pokemon)
                                    onPokemonClick(pokemon.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

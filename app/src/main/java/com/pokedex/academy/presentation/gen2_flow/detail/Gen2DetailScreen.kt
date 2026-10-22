package com.pokedex.academy.presentation.gen2_flow.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.core.theme.GreenFlow
import com.pokedex.academy.presentation.common.components.*
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Gen2DetailScreen(
    onBackClick: () -> Unit,
    viewModel: Gen2DetailViewModel = hiltViewModel()
) {
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Manejo de eventos efímeros emitidos por SharedFlow
    LaunchedEffect(Unit) {
        viewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is DetailEffect.NotifyAction -> {
                    snackbarHostState.showSnackbar(effect.text)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle (Flow & SharedFlow)") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GreenFlow,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            if (detailState is UiState.Success) {
                val pokemon = (detailState as UiState.Success).data
                FloatingActionButton(
                    onClick = { viewModel.toggleFavorite(pokemon.displayName) },
                    containerColor = GreenFlow,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorito")
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            PedagogicalBanner(
                title = "Detalle con StateFlow & SharedFlow",
                badgeText = "collectAsStateWithLifecycle()",
                badgeColor = GreenFlow,
                description = "El estado proviene de un StateFlow seguro para el ciclo de vida, y el botón flotante emite un SharedFlow para disparar el Snackbar."
            )

            when (val state = detailState) {
                is UiState.Idle, is UiState.Loading -> {
                    LoadingStateView(message = "Recolectando detalle con StateFlow...")
                }
                is UiState.Error -> {
                    ErrorStateView(
                        message = state.message,
                        onRetry = { viewModel.loadDetail() }
                    )
                }
                is UiState.Success -> {
                    PokemonDetailBody(pokemon = state.data)
                }
            }
        }
    }
}

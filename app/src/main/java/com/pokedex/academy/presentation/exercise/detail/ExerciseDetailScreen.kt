package com.pokedex.academy.presentation.exercise.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.core.theme.OrangeExercise
import com.pokedex.academy.presentation.common.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    onBackClick: () -> Unit,
    viewModel: ExerciseDetailViewModel = hiltViewModel()
) {
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle (Taller Práctico)") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OrangeExercise,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            PedagogicalBanner(
                title = "Taller: Detalle a completar",
                badgeText = "Ejercicio Alumnos",
                badgeColor = OrangeExercise,
                description = "El endpoint ya responde. Conecta el StateFlow o LiveData en 'ExerciseDetailViewModel.kt' para ver las estadísticas del Pokémon."
            )

            when (val state = detailState) {
                is UiState.Idle, is UiState.Loading -> {
                    LoadingStateView(message = "Esperando que el alumno complete el ViewModel...")
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

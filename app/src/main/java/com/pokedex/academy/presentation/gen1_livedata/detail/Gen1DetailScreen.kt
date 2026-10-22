package com.pokedex.academy.presentation.gen1_livedata.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.core.theme.BlueLiveData
import com.pokedex.academy.presentation.common.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Gen1DetailScreen(
    onBackClick: () -> Unit,
    viewModel: Gen1DetailViewModel = hiltViewModel()
) {
    val detailState by viewModel.detailState.observeAsState(initial = UiState.Loading)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle (LiveData)") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueLiveData,
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
                title = "Detalle con LiveData",
                badgeText = "observeAsState()",
                badgeColor = BlueLiveData,
                description = "El estado del detalle se observa mediante LiveData. Ideal para demostrar a los alumnos la forma tradicional de Jetpack."
            )

            when (val state = detailState) {
                is UiState.Idle, is UiState.Loading -> {
                    LoadingStateView(message = "Cargando estadísticas con LiveData...")
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

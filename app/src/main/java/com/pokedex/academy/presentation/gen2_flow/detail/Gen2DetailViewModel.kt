package com.pokedex.academy.presentation.gen2_flow.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.domain.model.PokemonDetail
import com.pokedex.academy.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DetailEffect {
    data class NotifyAction(val text: String) : DetailEffect
}

@HiltViewModel
class Gen2DetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId: String = checkNotNull(savedStateHandle["pokemonId"])

    // Estado principal mediante StateFlow
    private val _detailState = MutableStateFlow<UiState<PokemonDetail>>(UiState.Loading)
    val detailState: StateFlow<UiState<PokemonDetail>> = _detailState.asStateFlow()

    // Eventos one-shot mediante SharedFlow
    private val _effectFlow = MutableSharedFlow<DetailEffect>()
    val effectFlow: SharedFlow<DetailEffect> = _effectFlow.asSharedFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        _detailState.value = UiState.Loading
        viewModelScope.launch {
            when (val result = getPokemonDetailUseCase(pokemonId)) {
                is Resource.Success -> {
                    _detailState.value = UiState.Success(result.data)
                }
                is Resource.Error -> {
                    _detailState.value = UiState.Error(result.message)
                    _effectFlow.emit(DetailEffect.NotifyAction("Ocurrió un error: ${result.message}"))
                }
            }
        }
    }

    fun toggleFavorite(pokemonName: String) {
        viewModelScope.launch {
            _effectFlow.emit(DetailEffect.NotifyAction("⭐ $pokemonName añadido a tus favoritos (SharedFlow)"))
        }
    }
}

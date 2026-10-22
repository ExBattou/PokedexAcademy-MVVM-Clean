package com.pokedex.academy.presentation.exercise.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.domain.model.PokemonDetail
import com.pokedex.academy.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ==========================================================================================
 *  TALLER: DETALLE DE POKÉMON A MEDIO HACER
 * ==========================================================================================
 *
 * En este ViewModel el endpoint y caso de uso ya están inyectados, pero el alumno debe:
 * 1. Definir los flujos de estado (LiveData o StateFlow)
 * 2. Implementar 'loadDetail()'
 * 3. Agregar un SharedFlow para disparar un evento de captura o favorito.
 */
@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId: String = checkNotNull(savedStateHandle["pokemonId"])

    // TODO [ALUMNOS]: Reemplazar este placeholder por su implementación de LiveData o StateFlow
    private val _detailState = MutableStateFlow<UiState<PokemonDetail>>(
        UiState.Error("⚠️ [EJERCICIO DETALLE]: Completa la implementación reactiva en 'ExerciseDetailViewModel.kt'")
    )
    val detailState: StateFlow<UiState<PokemonDetail>> = _detailState.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        // TODO [ALUMNOS]:
        // 1. Emitir UiState.Loading
        // 2. Invocar 'getPokemonDetailUseCase(pokemonId)' dentro de viewModelScope
        // 3. Emitir UiState.Success o UiState.Error
        viewModelScope.launch {
            /*
            _detailState.value = UiState.Loading
            when (val result = getPokemonDetailUseCase(pokemonId)) {
                is Resource.Success -> _detailState.value = UiState.Success(result.data)
                is Resource.Error -> _detailState.value = UiState.Error(result.message)
            }
            */
        }
    }
}

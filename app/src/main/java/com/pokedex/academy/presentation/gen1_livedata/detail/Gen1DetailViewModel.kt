package com.pokedex.academy.presentation.gen1_livedata.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.domain.model.PokemonDetail
import com.pokedex.academy.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * DETALLE DE POKÉMON (VERSIÓN LIVEDATA)
 *
 * Muestra cómo recibir parámetros de navegación mediante 'SavedStateHandle'
 * inyectado por Hilt y exponer el detalle del Pokémon como LiveData.
 */
@HiltViewModel
class Gen1DetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId: String = checkNotNull(savedStateHandle["pokemonId"])

    private val _detailState = MutableLiveData<UiState<PokemonDetail>>(UiState.Loading)
    val detailState: LiveData<UiState<PokemonDetail>> = _detailState

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
                }
            }
        }
    }
}

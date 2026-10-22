package com.pokedex.academy.presentation.gen1_livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.domain.model.Pokemon
import com.pokedex.academy.domain.usecase.GetGen1PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * =========================================================================
 *  MÓDULO DIDÁCTICO 1: Implementación basada en LiveData (Kanto - Gen 1)
 * =========================================================================
 *
 * CONCEPTOS CLAVE PARA LA CLASE:
 * 1. Encapsulamiento del Estado:
 *    - _uiState es 'MutableLiveData' y PRIVADO: Sólo el ViewModel tiene la potestad de mutar el estado.
 *    - uiState es 'LiveData' (inmutable) y PÚBLICO: La vista (Fragment/Compose) sólo puede OBSERVAR.
 *
 * 2. Concurrencia y Threading en LiveData:
 *    - Si estamos en el hilo principal (Main Thread): usamos `.value = ...`
 *    - Si estuviéramos en un hilo de fondo (Background/IO): debemos usar `.postValue(...)`
 *    - Nota: Al usar viewModelScope.launch sin Dispatcher explícito, corremos por defecto en Main.
 *
 * 3. Dependencia de Android SDK:
 *    - Nota que LiveData pertenece al paquete 'androidx.lifecycle'. Está acoplado a Android,
 *      lo cual motivó la aparición de Kotlin StateFlow (puro en Kotlin).
 */
@HiltViewModel
class Gen1ListViewModel @Inject constructor(
    private val getGen1PokemonUseCase: GetGen1PokemonUseCase
) : ViewModel() {

    // 1. Estado mutable privado (Backing Property)
    private val _uiState = MutableLiveData<UiState<List<Pokemon>>>(UiState.Idle)

    // 2. Estado inmutable público expuesto a la vista
    val uiState: LiveData<UiState<List<Pokemon>>> = _uiState

    init {
        loadGen1Pokemon()
    }

    fun loadGen1Pokemon() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            when (val result = getGen1PokemonUseCase()) {
                is Resource.Success -> {
                    // En Main Thread asignamos directamente con .value
                    _uiState.value = UiState.Success(result.data)
                }
                is Resource.Error -> {
                    _uiState.value = UiState.Error(result.message)
                }
            }
        }
    }
}

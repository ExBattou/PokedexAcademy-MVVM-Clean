package com.pokedex.academy.presentation.gen2_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.domain.model.Pokemon
import com.pokedex.academy.domain.usecase.GetGen2PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Eventos efímeros (One-Shot Events) que no representan un estado persistente:
 * Navegaciones, Snackbars, Mensajes toast, Alertas de vibración.
 */
sealed interface Gen2UiEffect {
    data class ShowSnackbar(val message: String) : Gen2UiEffect
}

/**
 * =========================================================================
 *  MÓDULO DIDÁCTICO 2: Implementación moderna con Kotlin Flow (Johto - Gen 2)
 * =========================================================================
 *
 * CONCEPTOS CLAVE PARA LA CLASE:
 * 1. StateFlow para ESTADO DE PANTALLA:
 *    - Requiere un valor inicial obligatorio: MutableStateFlow(UiState.Idle)
 *    - Replay = 1: Cualquier nuevo coleccionista recibe inmediatamente el último estado.
 *    - Conflación: Si se emite el mismo valor idéntico dos veces seguidas, se descarta para evitar renders innecesarios.
 *    - Kotlin puro: No depende de android.jar, lo que facilita pruebas unitarias rápidas con coroutines-test.
 *
 * 2. SharedFlow para EVENTOS ONE-SHOT:
 *    - Resuelve el problema histórico de LiveData de volver a disparar eventos al rotar la pantalla
 *      (el molesto re-disparo de toasts o navegaciones).
 *    - No retiene el valor por defecto (replay = 0), por lo que un evento emitido se consume y desaparece.
 */
@HiltViewModel
class Gen2ListViewModel @Inject constructor(
    private val getGen2PokemonUseCase: GetGen2PokemonUseCase
) : ViewModel() {

    // 1. Estado persistente (StateFlow)
    private val _uiState = MutableStateFlow<UiState<List<Pokemon>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Pokemon>>> = _uiState.asStateFlow()

    // 2. Canal de eventos one-shot (SharedFlow)
    private val _effectFlow = MutableSharedFlow<Gen2UiEffect>()
    val effectFlow: SharedFlow<Gen2UiEffect> = _effectFlow.asSharedFlow()

    init {
        loadGen2Pokemon()
    }

    fun loadGen2Pokemon() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            when (val result = getGen2PokemonUseCase()) {
                is Resource.Success -> {
                    _uiState.value = UiState.Success(result.data)
                    // Emitimos un evento único informativo
                    _effectFlow.emit(Gen2UiEffect.ShowSnackbar("¡Se cargaron 100 Pokémon de Johto usando StateFlow!"))
                }
                is Resource.Error -> {
                    _uiState.value = UiState.Error(result.message)
                    _effectFlow.emit(Gen2UiEffect.ShowSnackbar("Error de carga: ${result.message}"))
                }
            }
        }
    }

    /**
     * Ejemplo de acción que dispara un evento efímero mediante SharedFlow
     */
    fun onPokemonAction(pokemon: Pokemon) {
        viewModelScope.launch {
            _effectFlow.emit(
                Gen2UiEffect.ShowSnackbar("⚡ Has seleccionado a ${pokemon.displayName} (${pokemon.formattedId})")
            )
        }
    }
}

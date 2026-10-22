package com.pokedex.academy.presentation.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.core.model.UiState
import com.pokedex.academy.domain.model.Pokemon
import com.pokedex.academy.domain.usecase.GetGen3PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ==========================================================================================
 *  TALLER PRÁCTICO PARA ALUMNOS: Implementación del Flujo Reactivo (Gen 3 - Hoenn)
 * ==========================================================================================
 *
 * ¡Bienvenido/a al ejercicio práctico!
 * La arquitectura limpia (Clean Architecture) ya está completamente cableada:
 *   - Remote Data Source (Retrofit) listo: PokeApiService.kt
 *   - Repositorio listo: PokemonRepositoryImpl.kt
 *   - Caso de Uso listo: GetGen3PokemonUseCase.kt
 *
 * TU MISIÓN COMO ALUMNO:
 * Elige (o sigue las instrucciones de tu profesor) si implementarás este ViewModel usando:
 *   OPCIÓN A: LiveData (MutableLiveData + LiveData)
 *   OPCIÓN B: Kotlin Coroutines Flow (MutableStateFlow + StateFlow) + SharedFlow para eventos
 *
 * SIGUE LOS PASOS 'TODO' DETALLADOS A CONTINUACIÓN:
 */
@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getGen3PokemonUseCase: GetGen3PokemonUseCase
) : ViewModel() {

    // --------------------------------------------------------------------------------------
    // TODO [ALUMNO - PASO 1]: Declara la variable mutable PRIVADA para almacenar el UiState.
    //
    // Si eliges LiveData:
    //    private val _exerciseState = MutableLiveData<UiState<List<Pokemon>>>(UiState.Idle)
    //
    // Si eliges StateFlow:
    //    private val _exerciseState = MutableStateFlow<UiState<List<Pokemon>>>(UiState.Idle)
    // --------------------------------------------------------------------------------------
    private val _exerciseState = MutableStateFlow<UiState<List<Pokemon>>>(
        UiState.Error("⚠️ [EJERCICIO PENDIENTE]: Abre ExerciseViewModel.kt y completa los TODOs para conectar el flujo reactivo.")
    )

    // --------------------------------------------------------------------------------------
    // TODO [ALUMNO - PASO 2]: Expón la variable inmutable PÚBLICA que consumirá la UI.
    //
    // Si eliges LiveData:
    //    val exerciseState: LiveData<UiState<List<Pokemon>>> = _exerciseState
    //
    // Si eliges StateFlow:
    //    val exerciseState: StateFlow<UiState<List<Pokemon>>> = _exerciseState.asStateFlow()
    // --------------------------------------------------------------------------------------
    val exerciseState: StateFlow<UiState<List<Pokemon>>> = _exerciseState.asStateFlow()

    // --------------------------------------------------------------------------------------
    // TODO [ALUMNO - PASO 3 (DESAFÍO FLOW)]:
    // Crea un MutableSharedFlow<String> para emitir un evento cada vez que el usuario
    // toque un Pokémon o la lista termine de cargar.
    // --------------------------------------------------------------------------------------

    init {
        loadGen3Pokemon()
    }

    fun loadGen3Pokemon() {
        // ----------------------------------------------------------------------------------
        // TODO [ALUMNO - PASO 4]:
        // 1. Asigna UiState.Loading al estado mutable.
        // 2. Dentro de viewModelScope.launch, invoca 'getGen3PokemonUseCase()'.
        // 3. Evalúa el resultado con 'when (val result = ...)':
        //      - is Resource.Success -> Asigna UiState.Success(result.data)
        //      - is Resource.Error -> Asigna UiState.Error(result.message)
        // ----------------------------------------------------------------------------------
        viewModelScope.launch {
            // Descomentar y completar una vez resuelto el ejercicio:
            /*
            _exerciseState.value = UiState.Loading
            when (val result = getGen3PokemonUseCase()) {
                is Resource.Success -> {
                    _exerciseState.value = UiState.Success(result.data)
                }
                is Resource.Error -> {
                    _exerciseState.value = UiState.Error(result.message)
                }
            }
            */
        }
    }
}

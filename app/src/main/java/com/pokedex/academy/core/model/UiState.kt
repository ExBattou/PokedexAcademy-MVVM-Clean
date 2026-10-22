package com.pokedex.academy.core.model

/**
 * Estado genérico para representar los diferentes momentos de la UI
 * en la arquitectura MVVM:
 * - Idle: Estado inicial
 * - Loading: Mientras la petición está en vuelo
 * - Success: Con los datos ya obtenidos
 * - Error: Si ocurrió una falla (con mensaje amigable)
 */
sealed interface UiState<out T> {
    data object Idle : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}

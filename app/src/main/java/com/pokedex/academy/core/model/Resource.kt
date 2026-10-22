package com.pokedex.academy.core.model

/**
 * Envoltorio para resultados provenientes de la capa de Data / Repositorio.
 */
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val cause: Throwable? = null) : Resource<Nothing>()
}

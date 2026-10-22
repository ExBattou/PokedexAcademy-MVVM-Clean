package com.pokedex.academy.domain.model

/**
 * Modelo de Dominio que representa un Pokémon simplificado para las listas.
 * Nota didáctica: Este modelo es KOTLIN PURO. No tiene dependencias de Android SDK ni de Retrofit/Moshi.
 */
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val generation: Int
) {
    val formattedId: String
        get() = "#%03d".format(id)

    val displayName: String
        get() = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

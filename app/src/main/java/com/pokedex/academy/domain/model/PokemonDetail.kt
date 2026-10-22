package com.pokedex.academy.domain.model

data class PokemonStat(
    val name: String,
    val value: Int,
    val maxValue: Int = 255
)

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Float, // en metros
    val weight: Float, // en kilogramos
    val types: List<String>,
    val abilities: List<String>,
    val stats: List<PokemonStat>,
    val baseExperience: Int
) {
    val formattedId: String
        get() = "#%03d".format(id)

    val displayName: String
        get() = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

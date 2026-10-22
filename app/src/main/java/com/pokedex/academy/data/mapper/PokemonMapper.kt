package com.pokedex.academy.data.mapper

import com.pokedex.academy.data.remote.dto.NamedApiResourceDto
import com.pokedex.academy.data.remote.dto.PokemonDetailResponseDto
import com.pokedex.academy.domain.model.Pokemon
import com.pokedex.academy.domain.model.PokemonDetail
import com.pokedex.academy.domain.model.PokemonStat

object PokemonMapper {

    /**
     * Extrae el ID numérico de la URL provista por PokeAPI
     * Ejemplo: "https://pokeapi.co/api/v2/pokemon/25/" -> 25
     */
    fun extractIdFromUrl(url: String): Int {
        val trimmed = url.trimEnd('/')
        return trimmed.substringAfterLast('/').toIntOrNull() ?: 0
    }

    /**
     * Construye la URL de la ilustración oficial en alta calidad
     */
    fun buildOfficialArtworkUrl(id: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }

    fun toDomain(dto: NamedApiResourceDto, generation: Int): Pokemon {
        val id = extractIdFromUrl(dto.url)
        return Pokemon(
            id = id,
            name = dto.name,
            imageUrl = buildOfficialArtworkUrl(id),
            generation = generation
        )
    }

    fun toDomain(dto: PokemonDetailResponseDto): PokemonDetail {
        val artworkUrl = dto.sprites?.other?.officialArtwork?.frontDefault
            ?: dto.sprites?.frontDefault
            ?: buildOfficialArtworkUrl(dto.id)

        // Convertir altura de decímetros a metros (ej: 7 -> 0.7m)
        val heightInMeters = dto.height / 10f
        // Convertir peso de hectogramos a kilogramos (ej: 60 -> 6.0kg)
        val weightInKg = dto.weight / 10f

        val types = dto.types
            .sortedBy { it.slot }
            .map { it.type.name.replaceFirstChar { char -> char.titlecase() } }

        val abilities = dto.abilities
            .sortedBy { it.slot }
            .map { entry ->
                val name = entry.ability.name.replace('-', ' ')
                    .replaceFirstChar { char -> char.titlecase() }
                if (entry.isHidden) "$name (Oculta)" else name
            }

        val stats = dto.stats.map { entry ->
            PokemonStat(
                name = formatStatName(entry.stat.name),
                value = entry.baseStat
            )
        }

        return PokemonDetail(
            id = dto.id,
            name = dto.name,
            imageUrl = artworkUrl,
            height = heightInMeters,
            weight = weightInKg,
            types = types,
            abilities = abilities,
            stats = stats,
            baseExperience = dto.baseExperience ?: 0
        )
    }

    private fun formatStatName(apiName: String): String {
        return when (apiName) {
            "hp" -> "HP"
            "attack" -> "Ataque"
            "defense" -> "Defensa"
            "special-attack" -> "Atq. Especial"
            "special-defense" -> "Def. Especial"
            "speed" -> "Velocidad"
            else -> apiName.replaceFirstChar { it.titlecase() }
        }
    }
}

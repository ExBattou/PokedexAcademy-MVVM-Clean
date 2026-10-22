package com.pokedex.academy.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonTypeEntryDto(
    @Json(name = "slot") val slot: Int,
    @Json(name = "type") val type: NamedApiResourceDto
)

@JsonClass(generateAdapter = true)
data class PokemonAbilityEntryDto(
    @Json(name = "ability") val ability: NamedApiResourceDto,
    @Json(name = "is_hidden") val isHidden: Boolean,
    @Json(name = "slot") val slot: Int
)

@JsonClass(generateAdapter = true)
data class PokemonStatEntryDto(
    @Json(name = "base_stat") val baseStat: Int,
    @Json(name = "effort") val effort: Int,
    @Json(name = "stat") val stat: NamedApiResourceDto
)

@JsonClass(generateAdapter = true)
data class OfficialArtworkDto(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_shiny") val frontShiny: String?
)

@JsonClass(generateAdapter = true)
data class OtherSpritesDto(
    @Json(name = "official-artwork") val officialArtwork: OfficialArtworkDto?
)

@JsonClass(generateAdapter = true)
data class SpritesDto(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "other") val other: OtherSpritesDto?
)

@JsonClass(generateAdapter = true)
data class PokemonDetailResponseDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "height") val height: Int, // decimetros
    @Json(name = "weight") val weight: Int, // hectogramos
    @Json(name = "base_experience") val baseExperience: Int?,
    @Json(name = "sprites") val sprites: SpritesDto?,
    @Json(name = "types") val types: List<PokemonTypeEntryDto>,
    @Json(name = "abilities") val abilities: List<PokemonAbilityEntryDto>,
    @Json(name = "stats") val stats: List<PokemonStatEntryDto>
)

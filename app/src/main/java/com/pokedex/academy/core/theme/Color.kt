package com.pokedex.academy.core.theme

import androidx.compose.ui.graphics.Color

val RedPrimary = Color(0xFFE53935)
val RedDark = Color(0xFFC62828)
val RedLight = Color(0xFFFFCDD2)

val BlueLiveData = Color(0xFF1E88E5)
val BlueLiveDataContainer = Color(0xFFBBDEFB)

val GreenFlow = Color(0xFF43A047)
val GreenFlowContainer = Color(0xFFC8E6C9)

val OrangeExercise = Color(0xFFFB8C00)
val OrangeExerciseContainer = Color(0xFFFFE0B2)

val BackgroundLight = Color(0xFFF7F9FC)
val SurfaceLight = Color(0xFFFFFFFF)
val CardBorder = Color(0xFFE0E0E0)

// Pokémon Type Colors
val TypeNormal = Color(0xFFA8A77A)
val TypeFire = Color(0xFFEE8130)
val TypeWater = Color(0xFF6390F0)
val TypeElectric = Color(0xFFF7D02C)
val TypeGrass = Color(0xFF7AC74C)
val TypeIce = Color(0xFF96D9D6)
val TypeFighting = Color(0xFFC22E28)
val TypePoison = Color(0xFFA33EA1)
val TypeGround = Color(0xFFE2BF65)
val TypeFlying = Color(0xFFA98FF3)
val TypePsychic = Color(0xFFF95587)
val TypeBug = Color(0xFFA6B91A)
val TypeRock = Color(0xFFB6A136)
val TypeGhost = Color(0xFF735797)
val TypeDragon = Color(0xFF6F35FC)
val TypeDark = Color(0xFF705746)
val TypeSteel = Color(0xFFB7B7CE)
val TypeFairy = Color(0xFFD685AD)

fun getPokemonTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "normal" -> TypeNormal
        "fire", "fuego" -> TypeFire
        "water", "agua" -> TypeWater
        "electric", "eléctrico" -> TypeElectric
        "grass", "planta" -> TypeGrass
        "ice", "hielo" -> TypeIce
        "fighting", "lucha" -> TypeFighting
        "poison", "veneno" -> TypePoison
        "ground", "tierra" -> TypeGround
        "flying", "volador" -> TypeFlying
        "psychic", "psíquico" -> TypePsychic
        "bug", "bicho" -> TypeBug
        "rock", "roca" -> TypeRock
        "ghost", "fantasma" -> TypeGhost
        "dragon", "dragón" -> TypeDragon
        "dark", "siniestro" -> TypeDark
        "steel", "acero" -> TypeSteel
        "fairy", "hada" -> TypeFairy
        else -> Color(0xFF757575)
    }
}

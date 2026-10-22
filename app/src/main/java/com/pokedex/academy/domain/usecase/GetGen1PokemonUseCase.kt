package com.pokedex.academy.domain.usecase

import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.domain.model.Pokemon
import com.pokedex.academy.domain.repository.PokemonRepository
import javax.inject.Inject

/**
 * Caso de Uso para obtener los 151 Pokémon de la 1ra Generación (Kanto).
 * En la arquitectura Limpia, cada caso de uso tiene una única responsabilidad.
 */
class GetGen1PokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(): Resource<List<Pokemon>> {
        // Kanto: 151 pokémon iniciando en offset 0
        return repository.getPokemonList(limit = 151, offset = 0, generation = 1)
    }
}

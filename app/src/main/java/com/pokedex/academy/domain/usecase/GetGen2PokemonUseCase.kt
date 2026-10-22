package com.pokedex.academy.domain.usecase

import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.domain.model.Pokemon
import com.pokedex.academy.domain.repository.PokemonRepository
import javax.inject.Inject

/**
 * Caso de Uso para obtener los 100 Pokémon de la 2da Generación (Johto).
 */
class GetGen2PokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(): Resource<List<Pokemon>> {
        // Johto: 100 pokémon (152 al 251), iniciando en offset 151
        return repository.getPokemonList(limit = 100, offset = 151, generation = 2)
    }
}

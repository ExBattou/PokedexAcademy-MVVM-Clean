package com.pokedex.academy.domain.usecase

import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.domain.model.PokemonDetail
import com.pokedex.academy.domain.repository.PokemonRepository
import javax.inject.Inject

/**
 * Caso de Uso para obtener el detalle exhaustivo de un Pokémon por su ID o Nombre.
 */
class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(nameOrId: String): Resource<PokemonDetail> {
        val query = nameOrId.trim().lowercase()
        if (query.isEmpty()) {
            return Resource.Error("El nombre o ID del Pokémon no puede estar vacío")
        }
        return repository.getPokemonDetail(query)
    }
}

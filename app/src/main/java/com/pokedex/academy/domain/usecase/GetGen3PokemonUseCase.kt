package com.pokedex.academy.domain.usecase

import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.domain.model.Pokemon
import com.pokedex.academy.domain.repository.PokemonRepository
import javax.inject.Inject

/**
 * Caso de Uso para obtener los Pokémon de la 3ra Generación (Hoenn).
 * Este caso de uso está provisto para que los ALUMNOS implementen su consumo
 * en el ViewModel de ejercicios (usando LiveData o StateFlow según elija el profesor).
 */
class GetGen3PokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(): Resource<List<Pokemon>> {
        // Hoenn: 135 pokémon (252 al 386), iniciando en offset 251
        return repository.getPokemonList(limit = 135, offset = 251, generation = 3)
    }
}

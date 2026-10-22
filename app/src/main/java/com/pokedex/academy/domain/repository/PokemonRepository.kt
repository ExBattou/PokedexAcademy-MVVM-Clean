package com.pokedex.academy.domain.repository

import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.domain.model.Pokemon
import com.pokedex.academy.domain.model.PokemonDetail

/**
 * Contrato del Repositorio definido en la capa de Domain.
 * Sigue el principio de Inversión de Dependencias (DIP) de SOLID:
 * El Dominio define QUÉ necesita, y la capa de Data implementa CÓMO obtenerlo.
 */
interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int, generation: Int): Resource<List<Pokemon>>
    suspend fun getPokemonDetail(nameOrId: String): Resource<PokemonDetail>
}

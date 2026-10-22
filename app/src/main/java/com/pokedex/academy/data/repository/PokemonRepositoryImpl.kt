package com.pokedex.academy.data.repository

import com.pokedex.academy.core.model.Resource
import com.pokedex.academy.data.mapper.PokemonMapper
import com.pokedex.academy.data.remote.PokeApiService
import com.pokedex.academy.domain.model.Pokemon
import com.pokedex.academy.domain.model.PokemonDetail
import com.pokedex.academy.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val apiService: PokeApiService
) : PokemonRepository {

    override suspend fun getPokemonList(
        limit: Int,
        offset: Int,
        generation: Int
    ): Resource<List<Pokemon>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getPokemonList(limit = limit, offset = offset)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val pokemons = body.results.map { dto ->
                        PokemonMapper.toDomain(dto, generation)
                    }
                    Resource.Success(pokemons)
                } else {
                    Resource.Error("Respuesta vacía del servidor")
                }
            } else {
                Resource.Error("Error HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Error de conexión. Verifica tu acceso a internet.", e)
        } catch (e: Exception) {
            Resource.Error("Error inesperado: ${e.localizedMessage}", e)
        }
    }

    override suspend fun getPokemonDetail(nameOrId: String): Resource<PokemonDetail> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPokemonDetail(nameOrId)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Resource.Success(PokemonMapper.toDomain(body))
                    } else {
                        Resource.Error("Detalle no encontrado para: $nameOrId")
                    }
                } else {
                    Resource.Error("Error HTTP ${response.code()}: No se encontró el Pokémon")
                }
            } catch (e: IOException) {
                Resource.Error("Error de red al consultar el detalle.", e)
            } catch (e: Exception) {
                Resource.Error("Error al cargar detalle: ${e.localizedMessage}", e)
            }
        }
}

package com.pokedex.academy.data.remote

import com.pokedex.academy.data.remote.dto.PokemonDetailResponseDto
import com.pokedex.academy.data.remote.dto.PokemonListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface Retrofit para la PokeAPI oficial (https://pokeapi.co/api/v2/)
 */
interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonListResponseDto>

    @GET("pokemon/{nameOrId}")
    suspend fun getPokemonDetail(
        @Path("nameOrId") nameOrId: String
    ): Response<PokemonDetailResponseDto>
}

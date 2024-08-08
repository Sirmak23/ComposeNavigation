package com.anddevcorp.composenavigation.data.repository

import com.anddevcorp.composenavigation.data.request.MovieRequest
import com.anddevcorp.composenavigation.model.MovieResponse


interface MovieRepository {
    suspend fun getPopularMovies(request: MovieRequest): Result<MovieResponse>
}

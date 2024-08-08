package com.anddevcorp.composenavigation.data.repository

import com.anddevcorp.composenavigation.data.request.MovieRequest
import com.anddevcorp.composenavigation.data.request.toMap
import com.anddevcorp.composenavigation.data.service.MovieService
import com.anddevcorp.composenavigation.model.MovieResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getPopularMovies(request: MovieRequest): Result<MovieResponse> {
        return try {
            val response = movieService.getPopularMovies(request.toMap())
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

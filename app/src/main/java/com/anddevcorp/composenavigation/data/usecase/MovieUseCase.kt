package com.anddevcorp.composenavigation.data.usecase

import com.anddevcorp.composenavigation.data.repository.MovieRepository
import com.anddevcorp.composenavigation.data.request.MovieRequest
import com.anddevcorp.composenavigation.model.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(request: MovieRequest): Flow<Result<MovieResponse>> = flow {
        val response = withContext(Dispatchers.IO) {
            movieRepository.getPopularMovies(request)
        }
        emit(response)
    }
}
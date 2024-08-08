package com.anddevcorp.composenavigation.model


sealed class MovieUiState(
    open val movies: MutableList<Movie>? = null
) {
    data class Success(override val movies: MutableList<Movie>) : MovieUiState(movies)

    data class Error(val error: String) : MovieUiState()

    data object Loading : MovieUiState()

    data object Empty : MovieUiState()
}

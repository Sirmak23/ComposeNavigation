package com.anddevcorp.composenavigation.di

import com.anddevcorp.composenavigation.data.repository.MovieRepository
import com.anddevcorp.composenavigation.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMovieRepository(loginRepositoryImpl: MovieRepositoryImpl): MovieRepository
}

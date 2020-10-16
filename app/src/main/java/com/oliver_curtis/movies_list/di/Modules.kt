package com.oliver_curtis.movies_list.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.oliver_curtis.movies_list.BuildConfig
import com.oliver_curtis.movies_list.common.retrofit.retrofitInstance
import com.oliver_curtis.movies_list.data.api.MovieService
import com.oliver_curtis.movies_list.data.db.MovieDatabase
import com.oliver_curtis.movies_list.data.repo.MovieDBRepository
import com.oliver_curtis.movies_list.domain.interactor.MovieUseCase
import com.oliver_curtis.movies_list.domain.interactor.MovieUseCaseImpl
import com.oliver_curtis.movies_list.domain.repo.MovieRepository
import com.oliver_curtis.movies_list.view.processor.MovieListProcessor
import com.oliver_curtis.movies_list.view.processor.impl.MovieListProcessorImpl
import com.oliver_curtis.movies_list.view.viewmodel.MovieViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class Modules {

    @Singleton
    @Provides
    fun providesGSONBuilder(): Gson{
        return GsonBuilder().setLenient().create()
    }

    @Singleton
    @Provides
    fun providesRetrofitInstance(): Retrofit {
        return retrofitInstance(BuildConfig.BASE_URL, providesGSONBuilder())
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(): MovieDatabase {
        return MovieDatabase(provideApiService(providesRetrofitInstance()))
    }

    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository {
        return MovieDBRepository(provideMovieDatabase())
    }

    @Provides
    @Singleton
    fun provideMovieUseCase(): MovieUseCase {
        return MovieUseCaseImpl(provideMovieRepository())
    }

    @Provides
    @Singleton
    fun providesViewModel(): MovieViewModel {
        return MovieViewModel(provideMovieUseCase())
    }

    @Provides
    @Singleton
    fun providesMovieProcessor(): MovieListProcessor {
        return MovieListProcessorImpl()
    }
}
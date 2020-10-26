package com.oliver_curtis.movies_list.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.oliver_curtis.movies_list.BuildConfig
import com.oliver_curtis.movies_list.common.retrofit.retrofitInstance
import com.oliver_curtis.movies_list.data.api.MovieService
import com.oliver_curtis.movies_list.data.source.impl.RemoteMovieDataSource
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
    fun providesRetrofitInstance(gson: Gson): Retrofit {
        return retrofitInstance(BuildConfig.BASE_URL, gson)
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDatabase(movieService: MovieService): RemoteMovieDataSource {
        return RemoteMovieDataSource(movieService)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(movieDatabase: RemoteMovieDataSource): MovieRepository {
        return MovieDBRepository(movieDatabase)
    }

    @Provides
    fun provideMovieUseCase(movieRepository: MovieRepository): MovieUseCase {
        return MovieUseCaseImpl(movieRepository)
    }

    @Singleton
    @Provides
    fun providesViewModel(movieUseCase: MovieUseCase): MovieViewModel {
        return MovieViewModel(movieUseCase)
    }

    @Singleton
    @Provides
    fun providesMovieProcessor(): MovieListProcessor {
        return MovieListProcessorImpl()
    }
}
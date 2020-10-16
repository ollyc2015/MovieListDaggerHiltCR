package com.oliver_curtis.movies_list.domain.interactor

import com.oliver_curtis.movies_list.domain.model.Movie


interface MovieUseCase {

    suspend fun fetchMovies(page:Int): List<Movie>?
}
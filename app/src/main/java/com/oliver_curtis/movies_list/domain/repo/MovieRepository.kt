package com.oliver_curtis.movies_list.domain.repo

import com.oliver_curtis.movies_list.domain.model.Movie


interface MovieRepository {

    suspend fun getMovies(page:Int): List<Movie>?
}
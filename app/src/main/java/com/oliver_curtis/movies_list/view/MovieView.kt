package com.oliver_curtis.movies_list.view

import androidx.annotation.StringRes
import com.oliver_curtis.movies_list.domain.model.Movie

interface MovieView {

    fun displayMovies(movies: List<Movie>)
    fun displayError(@StringRes error: Int)
}
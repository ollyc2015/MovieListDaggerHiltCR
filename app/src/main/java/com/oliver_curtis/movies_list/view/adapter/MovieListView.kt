package com.oliver_curtis.movies_list.view.adapter

import com.oliver_curtis.movies_list.domain.model.Movie


interface MovieListView {

    fun displayMovies(movies: List<Movie>)
}
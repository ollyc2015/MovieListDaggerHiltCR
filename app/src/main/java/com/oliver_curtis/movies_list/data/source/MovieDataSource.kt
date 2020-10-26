package com.oliver_curtis.movies_list.data.source

import com.oliver_curtis.movies_list.data.entity.MovieApiEntity


interface MovieDataSource {

    fun getMovies(page:Int): MovieApiEntity

}
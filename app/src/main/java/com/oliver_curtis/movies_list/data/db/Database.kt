package com.oliver_curtis.movies_list.data.db

import com.oliver_curtis.movies_list.data.entity.MovieApiEntity


interface Database {

    fun getMovies(page:Int): MovieApiEntity

}
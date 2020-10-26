package com.oliver_curtis.movies_list.data.source.impl

import com.oliver_curtis.movies_list.data.api.MovieService
import com.oliver_curtis.movies_list.data.entity.MovieApiEntity
import com.oliver_curtis.movies_list.data.source.MovieDataSource
import retrofit2.HttpException

class RemoteMovieDataSource(private val service: MovieService) : MovieDataSource {

    override fun getMovies(page:Int): MovieApiEntity {
        val response = service.getMovies(page).execute()

        if (response.isSuccessful) {
            return response.body() as MovieApiEntity
        } else {
            throw HttpException(response)
        }
    }
}
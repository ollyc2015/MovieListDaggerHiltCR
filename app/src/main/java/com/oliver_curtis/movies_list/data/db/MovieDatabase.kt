package com.oliver_curtis.movies_list.data.db

import com.oliver_curtis.movies_list.data.api.MovieService
import com.oliver_curtis.movies_list.data.entity.MovieApiEntity
import retrofit2.HttpException

class MovieDatabase(private val service: MovieService) : Database {

    override fun getMovies(page:Int): MovieApiEntity {
        val response = service.getMovies(page).execute()

        if (response.isSuccessful) {
            return response.body() as MovieApiEntity
        } else {
            throw HttpException(response)
        }
    }
}
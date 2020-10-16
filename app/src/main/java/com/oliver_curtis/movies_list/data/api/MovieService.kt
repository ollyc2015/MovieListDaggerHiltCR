package com.oliver_curtis.movies_list.data.api

import com.oliver_curtis.movies_list.BuildConfig
import com.oliver_curtis.movies_list.data.entity.MovieApiEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/3/movie/popular?api_key=${BuildConfig.API_KEY}&")
    fun getMovies(@Query("page") page: Int): Call<MovieApiEntity>
}
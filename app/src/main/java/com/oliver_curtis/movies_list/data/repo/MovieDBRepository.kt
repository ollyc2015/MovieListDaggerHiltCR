package com.oliver_curtis.movies_list.data.repo

import com.oliver_curtis.movies_list.common.date.formatDate
import com.oliver_curtis.movies_list.data.source.impl.RemoteMovieDataSource
import com.oliver_curtis.movies_list.data.entity.MovieDetailsApiEntity
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.domain.repo.MovieRepository


class MovieDBRepository(private val movieDatabase: RemoteMovieDataSource) : MovieRepository {

    private val movieList: MutableList<Movie>? = arrayListOf()

    override suspend fun getMovies(page: Int): List<Movie>? {
        return toMovie(movieDatabase.getMovies(page).results)
    }

    private fun toMovie(entity: List<MovieDetailsApiEntity>): List<Movie>? {

        entity.forEach {

            val id = it.id

            val posterPath = it.poster_path

            val title = it.title

            val votingAverage = it.vote_average

            val releaseDate = it.release_date
            val date = formatDate(releaseDate)

            movieList?.add(Movie(id, posterPath, title, votingAverage, date))

        }

        return movieList?.toList()

    }
}

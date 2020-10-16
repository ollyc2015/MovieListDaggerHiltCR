package com.oliver_curtis.movies_list.data.repo

import com.oliver_curtis.movies_list.common.date.formatDate
import com.oliver_curtis.movies_list.data.db.MovieDatabase
import com.oliver_curtis.movies_list.data.entity.MovieDetailsApiEntity
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.domain.repo.MovieRepository


class MovieDBRepository(private val movieDatabase: MovieDatabase) : MovieRepository {

    override suspend fun getMovies(page: Int): List<Movie>? {
        return movieDatabase.getMovies(page).results.map { toMovie(it) }
    }

    private fun toMovie(entity: MovieDetailsApiEntity): Movie {

        val id = entity.id

        val posterPath = entity.poster_path

        val title = entity.title

        val votingAverage = entity.vote_average

        val releaseDate = entity.release_date
        val date = formatDate(releaseDate)

        return Movie(id, posterPath, title, votingAverage, date)

    }
}

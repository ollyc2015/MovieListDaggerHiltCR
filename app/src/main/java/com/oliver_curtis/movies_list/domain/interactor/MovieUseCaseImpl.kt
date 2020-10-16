package com.oliver_curtis.movies_list.domain.interactor

import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.domain.repo.MovieRepository
import io.reactivex.Single


class MovieUseCaseImpl(private val repository: MovieRepository) : MovieUseCase {
    override suspend fun fetchMovies(page:Int): List<Movie>? = repository.getMovies(page)

}
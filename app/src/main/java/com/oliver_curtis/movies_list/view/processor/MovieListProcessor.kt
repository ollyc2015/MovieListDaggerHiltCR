package com.oliver_curtis.movies_list.view.processor

import com.oliver_curtis.movies_list.common.processor.ViewProcessor
import com.oliver_curtis.movies_list.common.viewmodel.CallResult
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.view.MovieView

interface MovieListProcessor : ViewProcessor<MovieView> {

    fun processMovieResponse(callResult: CallResult<List<Movie>?>)

}
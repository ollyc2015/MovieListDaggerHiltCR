package com.oliver_curtis.movies_list.view.processor.impl

import com.oliver_curtis.movies_list.common.viewmodel.CallResult
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.view.MovieView
import com.oliver_curtis.movies_list.view.processor.MovieListProcessor
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class MovieListProcessorImpl : MovieListProcessor {

    private var view: MovieView? = null

    override fun processMovieResponse(callResult: CallResult<List<Movie>?>) {
        if (callResult.hasResult()) {
            callResult.result()?.let { view?.displayMovies(it) }
        } else if(callResult.hasError()) {
            view?.displayError(callResult.error())
        }
    }

    override fun attachView(view: MovieView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}
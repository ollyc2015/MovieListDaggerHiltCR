package com.oliver_curtis.movies_list.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oliver_curtis.movies_list.common.viewmodel.CallResult
import com.oliver_curtis.movies_list.common.viewmodel.cr.DefaultDispatcherProvider
import com.oliver_curtis.movies_list.common.viewmodel.cr.DispatcherProvider
import com.oliver_curtis.movies_list.common.viewmodel.livedata.DefaultLiveDataProvider
import com.oliver_curtis.movies_list.common.viewmodel.livedata.LiveDataProvider
import com.oliver_curtis.movies_list.domain.interactor.MovieUseCase
import com.oliver_curtis.movies_list.domain.model.Movie
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieViewModel

constructor( private val movieUseCase: MovieUseCase,
             private val liveDataProvider: LiveDataProvider = DefaultLiveDataProvider(),
             private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()):ViewModel() {

    private var requestInProgress: Job? = null

    fun getMovies(page: Int): MutableLiveData<CallResult<List<Movie>?>> {

        return liveDataProvider.liveDataInstance<List<Movie>?>().apply {

            issueRequest({ movieUseCase.fetchMovies(page) }, { this.postValue(it) })

        }
    }

    private fun <T> issueRequest(
        request: suspend () -> T,
        resultReceiver: (CallResult<T>) -> Unit
    ) {
        val job = Job()
        val coroutineScope = CoroutineScope(dispatcherProvider.main() + job)

        coroutineScope.launch(dispatcherProvider.main()) {
            requestInProgress = job

            try {
                val result = ioThread { request.invoke() }
                resultReceiver.invoke(result)
            } finally {
                requestInProgress = null
            }
        }
    }

    private suspend fun <T> ioThread(f: suspend () -> T): CallResult<T> {
        return withContext(dispatcherProvider.io()) {
            try {
                CallResult(f.invoke())
            } catch (t: Throwable) {
                CallResult<T>(t)
            }
        }
    }
}

package com.oliver_curtis.movies_list.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.oliver_curtis.movies_list.common.viewmodel.livedata.DefaultLiveDataProvider
import com.oliver_curtis.firestoreexampleproject.common.viewmodel.rx.DefaultSchedulerProvider
import com.oliver_curtis.movies_list.common.viewmodel.CallResult
import com.oliver_curtis.movies_list.domain.interactor.MovieUseCase
import com.oliver_curtis.movies_list.domain.model.Movie
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    // mocked repository since that isn't the class under test, we can
    @Mock lateinit var defaultSchedulerProvider: DefaultSchedulerProvider
    @Mock lateinit var defaultLiveDataProvider: DefaultLiveDataProvider
    @Mock lateinit var useCase: MovieUseCase

    @Mock lateinit var movieObserver: Observer<CallResult<List<Movie>>>
    private val movieLiveData = MutableLiveData<CallResult<List<Movie>>>()

    // the actual class under test
    private lateinit var viewModel: MovieViewModel

    private val movie = Movie(1, "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg", "Hard Kill", 4.7, "2020-08-25")

    // runs once before each test method is run
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(defaultSchedulerProvider.io()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(defaultSchedulerProvider.ui()).thenReturn(Schedulers.trampoline())

        viewModel = MovieViewModel(useCase, defaultLiveDataProvider, defaultSchedulerProvider)
    }

    @Test
    fun `givenSingleMovieEntity_whenObservingGetMovies_thenChangesObserved`() {
        val expected = listOf(movie)

        Mockito.`when`(defaultLiveDataProvider.liveDataInstance<List<Movie>>()).thenReturn(movieLiveData)
        Mockito.`when`(useCase.fetchMovies(1)).thenReturn(Single.just(expected))

        // call our methods under test and apply our observer whilst we are doing it.
        viewModel.getMovies(1).observeForever(movieObserver)

        // run assertion against observer
        argumentCaptor<CallResult<List<Movie>>>().apply {
            verify(movieObserver).onChanged(capture())
        }.run { Assert.assertEquals(expected, firstValue.result()) }
    }

    @Test
    fun `givenErrorWhilstObservingGetMovies_thenErrorObserved`() {
        val throwable = Throwable("Nasty Error")

        Mockito.`when`(defaultLiveDataProvider.liveDataInstance<List<Movie>>()).thenReturn(movieLiveData)
        // manipulate the response to trigger an error state "Single.error(throwable)"
        Mockito.`when`(useCase.fetchMovies(1)).thenReturn(Single.error(throwable))

        // call our methods under test and apply our observer whilst we are doing it.
        viewModel.getMovies(1).observeForever(movieObserver)

        // run assertion against observer
        argumentCaptor<CallResult<List<Movie>>>().apply {
            verify(movieObserver).onChanged(capture())
        }.run { Assert.assertEquals(throwable, firstValue.error()) }
    }

}
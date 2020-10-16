package com.oliver_curtis.movies_list.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oliver_curtis.movies_list.R
import com.oliver_curtis.movies_list.common.viewmodel.CallResult
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.view.adapter.MovieListAdapter
import com.oliver_curtis.movies_list.view.adapter.MovieListView
import com.oliver_curtis.movies_list.view.processor.MovieListProcessor
import com.oliver_curtis.movies_list.view.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragement_movie_list.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment(),MovieView, MovieListView {

    @Inject
    lateinit var viewModel: MovieViewModel
    @Inject
    lateinit var processor: MovieListProcessor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragement_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {

        bindListeners()
        initRecyclerView()
        detectBottomOfMovies()
    }

    private fun bindListeners() {
        movieRequestStatus.setRetryListener { fetchMovies() }
    }

    private fun initRecyclerView() {
        with(movieList) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MovieListAdapter(arrayListOf())
            adapter?.stateRestorationPolicy=RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    override fun onResume() {
        super.onResume()

        processor.attachView(this)
        fetchMovies()
    }

    private fun fetchMovies() {
        movieRequestStatus.showProgress()
        getMoviesFromDatabase(PAGE_NUMBER).observe(viewLifecycleOwner, getMoviesObserver)
        PAGE_NUMBER++
    }

    private var getMoviesObserver = object : Observer<CallResult<List<Movie>?>> {
        override fun onChanged(t: CallResult<List<Movie>?>) {
            processMovieResponse(t)
        }
    }

    private fun processMovieResponse(callResult: CallResult<List<Movie>?>) {

        processor.processMovieResponse(callResult)
    }

    override fun displayMovies(movies: List<Movie>) {

        movieRequestStatus.showSuccess()
        (movieList.adapter as MovieListAdapter).updateAll(movies)
    }

    override fun displayError(error: Throwable) {
        toast(error.toString())
        Log.d("olly", "error: $error")
    }

    private fun toast(string: String) {

        Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
    }

    private fun getMoviesFromDatabase(page:Int): MutableLiveData<CallResult<List<Movie>?>> {

        return viewModel.getMovies(page)
    }

    private fun detectBottomOfMovies() {

        movieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    getMoviesFromDatabase(PAGE_NUMBER).observe(viewLifecycleOwner, getMoviesObserver)
                    PAGE_NUMBER++
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()

        processor.also {
            it.detachView()
        }
    }

    companion object{

        var PAGE_NUMBER = 1

    }
}
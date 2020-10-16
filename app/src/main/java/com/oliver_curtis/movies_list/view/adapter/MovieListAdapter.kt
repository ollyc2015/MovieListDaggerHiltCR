package com.oliver_curtis.movies_list.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oliver_curtis.movies_list.R
import com.oliver_curtis.movies_list.domain.model.Movie


class MovieListAdapter(private val movies: MutableList<Movie>): RecyclerView.Adapter<MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindLaunch(movies[position])
    }

    fun updateAll(updated: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(MovieDiff(movies, updated), false)
        movies.clear()
        movies.addAll(updated)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
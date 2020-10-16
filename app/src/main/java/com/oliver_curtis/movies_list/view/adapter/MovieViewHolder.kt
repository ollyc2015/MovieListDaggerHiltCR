package com.oliver_curtis.movies_list.view.adapter

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.oliver_curtis.movies_list.BuildConfig
import com.oliver_curtis.movies_list.R
import com.oliver_curtis.movies_list.domain.model.Movie

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindLaunch(movie: Movie) {
        val resources = itemView.resources
        setMoviePoster(movie)
        bindNameAndDate(movie, resources)
    }

    private fun setMoviePoster(movie: Movie) {
        val progressView = itemView.findViewById<View>(R.id.moviePosterProgress).apply { visibility = View.VISIBLE }
        val loadingListener = ImageLoadingListener(progressView)
        val posterPath = BuildConfig.POSTER_BASE_URL + movie.poster_path

        Glide.with(itemView)
            .load(posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .fallback(R.drawable.ic_launcher_foreground)
            .listener(loadingListener)
            .into(itemView.findViewById(R.id.moviePoster))
    }

    private fun bindNameAndDate(movie: Movie, resources: Resources) {

        itemView.findViewById<TextView>(R.id.movieTitle).text = movie.title
        itemView.findViewById<TextView>(R.id.votingAverage).text = resources.getString(R.string.voting_average, movie.voting_average.toString())
        itemView.findViewById<TextView>(R.id.releaseDate).text = resources.getString(R.string.release_date, movie.release_date)
    }
}

private class ImageLoadingListener(private val progressView: View) : RequestListener<Drawable> {

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        progressView.visibility = View.GONE
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        progressView.visibility = View.GONE
        return false
    }
}
package com.oliver_curtis.movies_list.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.oliver_curtis.movies_list.domain.model.Movie

class MovieDiff(
    private val oldItemList: List<Movie>,
    private val newItemList: List<Movie>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItemList.size
    }

    override fun getNewListSize(): Int {
        return newItemList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemList[oldItemPosition].id == newItemList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemList[oldItemPosition]
        val newItem = newItemList[newItemPosition]

        return oldItem.title == newItem.title &&
                oldItem.release_date == newItem.release_date &&
                oldItem.voting_average == newItem.voting_average &&
                oldItem.poster_path == newItem.poster_path
    }
}
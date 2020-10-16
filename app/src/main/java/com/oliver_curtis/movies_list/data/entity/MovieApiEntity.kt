package com.oliver_curtis.movies_list.data.entity

data class MovieApiEntity(
     val page: Int,
     val results: List<MovieDetailsApiEntity>,
     val total_pages: Int,
     val total_results: Int
)
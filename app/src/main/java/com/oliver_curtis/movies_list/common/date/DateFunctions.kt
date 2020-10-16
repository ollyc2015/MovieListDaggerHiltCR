package com.oliver_curtis.movies_list.common.date

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT_FROM_DATABASE = "yyyy-MM-dd"
private const val DATE_FORMAT_UK = "dd/MM/yyyy"

fun formatDate(releaseDate: String): String {

    val sdf = SimpleDateFormat(DATE_FORMAT_FROM_DATABASE)
    val d: Date = sdf.parse(releaseDate)
    sdf.applyPattern(DATE_FORMAT_UK)
    return sdf.format(d)
}
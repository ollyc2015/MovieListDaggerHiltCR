package com.oliver_curtis.movies_list.common.viewmodel.error

interface ErrorResolver {

    fun findErrorMessageResId(error: Throwable): Int
}
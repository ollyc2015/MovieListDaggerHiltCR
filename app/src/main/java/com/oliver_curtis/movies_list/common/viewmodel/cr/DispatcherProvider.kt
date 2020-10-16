package com.oliver_curtis.movies_list.common.viewmodel.cr

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun main(): CoroutineDispatcher

    fun io(): CoroutineDispatcher
}
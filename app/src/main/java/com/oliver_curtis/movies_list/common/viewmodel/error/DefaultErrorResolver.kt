package com.oliver_curtis.movies_list.common.viewmodel.error


import com.oliver_curtis.movies_list.R
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class DefaultErrorResolver : ErrorResolver {

    override fun findErrorMessageResId(error: Throwable): Int {
        return when (error) {
            is HttpException -> findErrorResId(error)
            is UnknownHostException -> R.string.network_error
            is SocketException -> R.string.network_error
            is SocketTimeoutException -> R.string.timeout_error
            else -> R.string.unknown_error
        }
    }

    private fun findErrorResId(error: HttpException): Int {
        return when (error.code()) {
            HttpURLConnection.HTTP_FORBIDDEN -> R.string.forbidden_error
            HttpURLConnection.HTTP_CLIENT_TIMEOUT -> R.string.timeout_error
            HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> R.string.timeout_error
            HttpURLConnection.HTTP_INTERNAL_ERROR -> R.string.server_error
            else -> R.string.unknown_error
        }
    }
}
package com.oliver_curtis.movies_list.common.retrofit

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


fun retrofitInstance(baseUrl: String, gson: Gson, headers: Map<String, String> = mapOf()): Retrofit {

    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    val httpClient = OkHttpClient.Builder()
    httpClient.connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS).build()

    httpClient.addInterceptor(logging)
    if (headers.isNotEmpty()) {
        httpClient.addInterceptor(getHeadersInterceptor(headers))
    }

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient.build())
        .build()
}

private fun getHeadersInterceptor(headers: Map<String, String>): Interceptor {
    return Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
        headers.forEach { header ->
            request.removeHeader(header.key)
            request.addHeader(header.key, header.value)
        }
        request.method(original.method(), original.body())
        chain.proceed(request.build())
    }
}
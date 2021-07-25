package com.example.dindintest.data.apimgr

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class APIManager(private val baseUrl: String, private val loggable: Boolean) {


    fun <T> build(serviceClazz: Class<T>): T {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.followRedirects(false)
        httpClientBuilder.followSslRedirects(false)
        httpClientBuilder.addInterceptor {
            val request = it.request()
            val url = request.url.toString().replace("%3D", "=")
            val response = it.proceed(
                request.newBuilder().url(url).run {
                    addHeader(
                        "Accept-Language",
                        Locale.getDefault().language
                    )
                    this
                }.build()
            )
            response
        }

        val loggingInterceptor = HttpLoggingInterceptor()
        if (loggable) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

//        httpClientBuilder.cache(Cache(context.cacheDir, CACHE_SIZE_BYTES))

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build()

        return retrofit.create(serviceClazz)
    }
}
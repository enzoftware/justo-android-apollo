package com.enzoftware.androidgraphql.di

import com.apollographql.apollo.ApolloClient
import com.enzoftware.androidgraphql.BuildConfig
import com.enzoftware.androidgraphql.core.HTTP_REQUEST_TIMEOUT_SECONDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(HTTP_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(HTTP_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(HTTP_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)

    @Provides
    fun provideOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient =
        okHttpClientBuilder.build()

    @Provides
    fun provideApolloClient(
        okHttpClient: OkHttpClient
    ): ApolloClient =
        ApolloClient.builder()
            .serverUrl(BuildConfig.GRAPHQL_URL)
            .okHttpClient(okHttpClient)
            .build()
}
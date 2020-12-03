package com.enzoftware.androidgraphql.di

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.enzoftware.androidgraphql.core.CoroutinesDispatchers
import com.enzoftware.androidgraphql.data.ProductsRemoteDataSource
import com.enzoftware.androidgraphql.data.ProductsRepository
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */
@Component(modules = [NetworkModule::class, ProductModule::class])
@Singleton
interface CoreComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CoreComponent
    }

    fun provideApplication(): Application

    fun provideApolloClient(): ApolloClient

    fun provideCoroutinesDispatchers(): CoroutinesDispatchers

    fun provideOkHttpClientBuilder(): OkHttpClient.Builder

    fun provideProductsRemoteDataSource(): ProductsRemoteDataSource

    fun provideProductsRepository(): ProductsRepository

}
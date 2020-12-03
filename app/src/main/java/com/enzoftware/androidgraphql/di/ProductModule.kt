package com.enzoftware.androidgraphql.di

import com.apollographql.apollo.ApolloClient
import com.enzoftware.androidgraphql.data.ProductsRemoteDataSource
import com.enzoftware.androidgraphql.data.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */
@Module
@InstallIn(ApplicationComponent::class)
object ProductModule {

    @Provides
    fun provideProductsRemoteDataSource(apolloClient: ApolloClient) =
        ProductsRemoteDataSource(apolloClient)

    @Provides
    fun provideProductsRepository(productsRemoteDataSource: ProductsRemoteDataSource) =
        ProductsRepository(productsRemoteDataSource)
}
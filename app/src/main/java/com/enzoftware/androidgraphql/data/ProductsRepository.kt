package com.enzoftware.androidgraphql.data


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */
class ProductsRepository(private val remoteDataSource: ProductsRemoteDataSource) {

    suspend fun fetchProducts() = remoteDataSource.fetchProductsRemote()

}
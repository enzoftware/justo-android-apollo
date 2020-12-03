package com.enzoftware.androidgraphql.data

import com.apollographql.apollo.ApolloClient
import com.enzoftware.android.graphql.FetchProductsQuery
import com.enzoftware.androidgraphql.core.Result
import com.enzoftware.androidgraphql.core.error
import com.enzoftware.androidgraphql.core.fetch
import com.enzoftware.androidgraphql.core.success
import com.enzoftware.androidgraphql.domain.model.Product
import com.enzoftware.androidgraphql.domain.model.toProducts


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */
class ProductsRemoteDataSource(private val apolloClient: ApolloClient) {

    suspend fun fetchProductsRemote(): Result<List<Product>> {
        val productsQuery = FetchProductsQuery()
        val res = apolloClient.fetch(productsQuery)
        val products = res.success()?.products
        if (products != null) return Result.Success(products.toProducts())
        return Result.Error(res.error())
    }
}
package com.enzoftware.androidgraphql.domain.usecase

import com.enzoftware.androidgraphql.data.ProductsRepository
import javax.inject.Inject


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */
class FetchProductsUseCase @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend fun getProducts() = productsRepository.fetchProducts()
}
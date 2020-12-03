package com.enzoftware.androidgraphql.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enzoftware.androidgraphql.core.CoroutinesDispatchers
import com.enzoftware.androidgraphql.core.Result
import com.enzoftware.androidgraphql.domain.model.Product
import com.enzoftware.androidgraphql.domain.usecase.FetchProductsUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Enzo Lizama Paredes on 12/1/20.
 * Contact: lizama.enzo@gmail.com
 */
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: FetchProductsUseCase,
    private val coroutineDispatcher: CoroutinesDispatchers
) : ViewModel() {

    private val _productsUiModelState = MutableLiveData<ProductsUiModel>()

    val productsUiModelState: LiveData<ProductsUiModel>
        get() = _productsUiModelState

    fun getProducts() {
        viewModelScope.launch(coroutineDispatcher.io) {
            val result = getProductsUseCase.getProducts()
            withContext(coroutineDispatcher.main) {
                when (result) {
                    is Result.Success -> getProductsSuccess(result.data)
                    is Result.Error -> getProductsError(result.exception)
                }
            }
        }
    }

    private fun getProductsSuccess(products: List<Product>?) {
        emitProductsUiState(products = products)
    }

    private fun getProductsError(exception: Exception?) {
        emitProductsUiState(exception = exception)
    }

    private fun emitProductsUiState(
        showProgress: Boolean = false,
        products: List<Product>? = null,
        exception: Exception? = null
    ) {
        val productsUiModel = ProductsUiModel(showProgress, products, exception)
        _productsUiModelState.value = productsUiModel
    }
}

data class ProductsUiModel(
    val showProgress: Boolean,
    val products: List<Product>?,
    val exception: Exception?
)
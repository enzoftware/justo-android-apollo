package com.enzoftware.androidgraphql.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.enzoftware.androidgraphql.R
import com.enzoftware.androidgraphql.core.invisible
import com.enzoftware.androidgraphql.core.liveDataObserve
import com.enzoftware.androidgraphql.core.visible
import com.enzoftware.androidgraphql.databinding.ActivityMainBinding
import com.enzoftware.androidgraphql.di.ViewModelProviderFactory
import com.enzoftware.androidgraphql.domain.model.Product
import com.enzoftware.androidgraphql.viewmodel.ProductsUiModel
import com.enzoftware.androidgraphql.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory<ProductsViewModel>

    private val productsViewModel by viewModels<ProductsViewModel> { viewModelProviderFactory }

    private val productsAdapter by lazy { ProductAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRefresh()
        initObservers()
        initRecycler()
        getProducts()
    }

    private fun initRecycler() {
        binding.productsRecyclerView.run {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun initObservers() {
        liveDataObserve(
            productsViewModel.productsUiModelState,
            { productsUi(it ?: return@liveDataObserve) }
        )
    }

    private fun productsUi(productsUiModel: ProductsUiModel) = productsUiModel.run {
        binding.productsRefresh.isRefreshing = showProgress
        if (products != null) productsUiSuccess(products)
        if (exception != null) productsUiError()
    }

    private fun productsUiError() {

    }

    private fun productsUiSuccess(products: List<Product>) {
        productsAdapter.set(products)
        if (products.isEmpty()) {
            binding.productsRecyclerView.invisible()
        } else {
            binding.productsRecyclerView.visible()
        }
    }

    private fun initRefresh() = binding.productsRefresh.run {
        isRefreshing = true
        setColorSchemeResources(R.color.colorAccent)
        setOnRefreshListener { getProducts() }
    }

    private fun getProducts() = productsViewModel.getProducts()


}


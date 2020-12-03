package com.enzoftware.androidgraphql.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */
@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory<VM : ViewModel> @Inject constructor(private val provider: Provider<VM>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        provideViewModel(modelClass, viewModel())

    private fun <T : ViewModel> provideViewModel(modelClass: Class<T>, viewModel: VM): T {
        return if (modelClass.isInstance(viewModel)) viewModel() as T
        else throw IllegalArgumentException("unknown model class ${viewModel.javaClass}")
    }

    private fun viewModel(): VM = provider.get()
}
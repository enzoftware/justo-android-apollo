package com.enzoftware.androidgraphql

import android.app.Activity
import android.app.Application
import android.content.Context
import com.enzoftware.androidgraphql.di.CoreComponent
import com.enzoftware.androidgraphql.di.DaggerCoreComponent
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */
@HiltAndroidApp
class App : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder().application(this).build()
    }


    companion object {
        @JvmStatic
        fun coreComponent(context: Context) = (context.applicationContext as App).coreComponent
    }
}

fun Activity.coreComponent() = App.coreComponent(this)

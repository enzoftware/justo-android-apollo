package com.enzoftware.androidgraphql.core

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */

fun String.Companion.empty() = ""

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.liveDataObserve(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))
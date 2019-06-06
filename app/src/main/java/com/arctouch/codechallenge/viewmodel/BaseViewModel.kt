package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel(){
    var disposable: Disposable? = null

    fun clearDisposable() {
        disposable?.dispose()
    }
}
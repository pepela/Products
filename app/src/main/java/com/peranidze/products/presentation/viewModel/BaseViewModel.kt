package com.peranidze.products.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel<State>(private val initialState: State) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _state = MutableLiveData(initialState)
    val state: LiveData<State>
        get() = _state

    protected fun changeState(changeState: (currentState: State) -> State) {
        _state.value = changeState(_state.value ?: initialState)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun Disposable.addToDisposables(): Disposable = apply { disposables.add(this) }
}

package com.peranidze.products.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface ViewModelAssistedFactory<V : ViewModel> {
    fun create(handle: SavedStateHandle): V
}

package com.peranidze.products.presentation

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val assistedFactories: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModelAssistedFactory<out ViewModel>>>
) {

    fun create(owner: SavedStateRegistryOwner, defaultArgs: Bundle? = null) =
        object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val viewModel =
                    createAssistedInjectViewModel(modelClass, handle)
                        ?: throw IllegalArgumentException("Unknown ViewModel class $modelClass")

                try {
                    return viewModel as T
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
        }

    private fun <T : ViewModel?> createAssistedInjectViewModel(
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): ViewModel? {
        val creator = assistedFactories[modelClass] ?: assistedFactories.asIterable()
            .firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
        ?: return null

        return creator.get().create(handle)
    }
}

@MainThread
fun SavedStateRegistryOwner.withFactory(
    factory: ViewModelFactory,
    defaultArgs: Bundle? = null
) = factory.create(this, defaultArgs)

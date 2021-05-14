package com.darotpeacedude.mvisample.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darotpeacedude.mvisample.data.api.ApiHelper
import com.darotpeacedude.mvisample.data.repository.MainRepository
import com.darotpeacedude.mvisample.ui.main.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
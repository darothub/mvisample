package com.darotpeacedude.mvisample.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darotpeacedude.mvisample.data.repository.MainRepository
import com.darotpeacedude.mvisample.ui.main.intent.MainIntent
import com.darotpeacedude.mvisample.ui.main.viewstate.MainViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchUser -> fetchUser()
                }
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            _state.value = MainViewState.Loading
            _state.value = try {
                MainViewState.Users(repository.getUsers())
            } catch (e: Exception) {
                MainViewState.Error(e.localizedMessage)
            }
        }
    }
}
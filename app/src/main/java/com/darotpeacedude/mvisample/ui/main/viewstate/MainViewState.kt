package com.darotpeacedude.mvisample.ui.main.viewstate

import com.darotpeacedude.mvisample.data.model.User

sealed class MainViewState {

    object Idle : MainViewState()
    object Loading : MainViewState()
    data class Users(val user: List<User>) : MainViewState()
    data class Error(val error: String?) : MainViewState()

}
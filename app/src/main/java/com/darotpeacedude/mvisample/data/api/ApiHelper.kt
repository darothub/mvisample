package com.darotpeacedude.mvisample.data.api

import com.darotpeacedude.mvisample.data.model.User

interface ApiHelper {

    suspend fun getUsers(): List<User>

}
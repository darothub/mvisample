package com.darotpeacedude.mvisample.data.api

import com.darotpeacedude.mvisample.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>
}
package com.darotpeacedude.mvisample.data.repository

import com.darotpeacedude.mvisample.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}
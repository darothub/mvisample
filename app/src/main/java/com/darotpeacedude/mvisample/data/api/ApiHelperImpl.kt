package com.darotpeacedude.mvisample.data.api

import com.darotpeacedude.mvisample.data.api.ApiServiceBuilder.apiService
import com.darotpeacedude.mvisample.data.model.User

internal class ApiHelperImpl(apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}
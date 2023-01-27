package com.example.jetpackcomposeinstagram.instagram.data.network

import com.example.jetpackcomposeinstagram.instagram.data.network.request.LoginRequest
import com.example.jetpackcomposeinstagram.instagram.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginClient {

    @GET("/v3/f78c9d33-28b1-4f81-9cf1-6d6ff78fa014")
    suspend fun doLogin(
        @Header("USER") user: LoginRequest
    ): Response<LoginResponse>
}
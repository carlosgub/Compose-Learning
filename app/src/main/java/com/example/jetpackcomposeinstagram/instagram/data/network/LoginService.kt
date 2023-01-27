package com.example.jetpackcomposeinstagram.instagram.data.network

import com.example.jetpackcomposeinstagram.instagram.data.network.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class LoginService @Inject constructor(
    private val loginClient: LoginClient
) {
    suspend fun doLogin(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val response = loginClient.doLogin(
                LoginRequest(
                    email, password
                )
            )
            response.body()?.success ?: false
        }
    }
}
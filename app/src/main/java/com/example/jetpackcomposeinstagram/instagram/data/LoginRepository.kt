package com.example.jetpackcomposeinstagram.instagram.data

import com.example.jetpackcomposeinstagram.instagram.data.network.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LoginService
) {
    suspend fun doLogin(email: String, password: String): Boolean =
        api.doLogin(email, password)
}
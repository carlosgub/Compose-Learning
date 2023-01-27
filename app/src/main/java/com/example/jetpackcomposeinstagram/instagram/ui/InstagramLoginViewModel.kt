package com.example.jetpackcomposeinstagram.instagram.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposeinstagram.instagram.model.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstagramLoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {


    private val _userField = MutableLiveData<String>()
    val userField: LiveData<String> = _userField

    private val _passwordField = MutableLiveData<String>()
    val passwordField: LiveData<String> = _passwordField

    private val _loginMessage = MutableLiveData<String>()
    val loginMessage: LiveData<String> = _loginMessage

    private val _isLoginEnabled = MutableLiveData<Boolean>()
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    fun userFieldChange(user: String) {
        _userField.value = user
        enableLogin(user, passwordField.value.orEmpty())
    }

    fun passwordFieldChange(password: String) {
        _passwordField.value = password
        enableLogin(userField.value.orEmpty(), password)
    }

    private fun enableLogin(email: String, password: String) {
        _isLoginEnabled.value =
            Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6
    }

    fun login() {
        viewModelScope.launch {
            val result = loginUseCase(userField.value.orEmpty(), passwordField.value.orEmpty())
            if (result) {
                _loginMessage.value = "Success"
            } else {
                _loginMessage.value = "Error"
            }
        }
    }

}
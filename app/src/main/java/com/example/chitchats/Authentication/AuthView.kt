package com.example.chitchats.Authentication

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthView @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(authState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: signInData){
        _state.update { it.copy(
            isSignedIn = result.data != null,
            signInError = result.error
        ) }
    }

    fun resetState(){
        _state.update { authState() }
    }
}
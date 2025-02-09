package com.example.chitchats.Authentication


data class authState(
    val isSignedIn : Boolean = false,
    val signInError : String? = null
)
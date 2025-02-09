package com.example.chitchats.Authentication

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext


@Composable
fun signInScreen(
    state : authState,
    onSignInClick : () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()){
        Button(onClick = onSignInClick) {
            Text("Sign in")
        }
    }
}
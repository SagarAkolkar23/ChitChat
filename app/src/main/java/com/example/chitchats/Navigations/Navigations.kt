package com.example.chitchats.Navigations


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import com.example.chitchats.Authentication.AuthView
import com.example.chitchats.Authentication.GoogleAuthUiClient
import com.example.chitchats.Authentication.signInScreen
import com.example.chitchats.ChatBackend.ChatState
import com.example.chitchats.Screens.Home
import kotlinx.coroutines.launch


@Composable
fun Navigates(){
    val navController = rememberNavController()
    val context = LocalContext.current
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    val view = AuthView()
    val state by view.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = "signIn") {

        composable("signIn") {

            LaunchedEffect(key1 = Unit) {
                if(googleAuthUiClient.getSignedInUser() != null){
                    navController.navigate("Home")
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult ={ result ->
                    if(result.resultCode == RESULT_OK){
                        coroutineScope.launch {
                            val signInData = googleAuthUiClient.getSignInData(
                                intent = result.data ?: return@launch
                            )
                            view.onSignInResult(signInData)
                        }
                    }
                }
            )
            LaunchedEffect(key1 = state.isSignedIn) {
                if(state.isSignedIn){
                    Toast.makeText(context, "Signed in", Toast.LENGTH_SHORT).show()
                    navController.navigate("Home"){
                        popUpTo("signInScreen"){ inclusive = true }
                    }
                    view.resetState()
                }
            }
            signInScreen(state = state,
                onSignInClick = {
                    coroutineScope.launch {
                        val signInIntendSender = googleAuthUiClient.signIn()
                        signInIntendSender?.let {
                            launcher.launch(IntentSenderRequest.Builder(it).build())
                        }
                    }
                    Log.d("signIn", "${view.state}")
                }
            )
        }
        composable("Home") {
            Home(
                onSignOutClick = {
                coroutineScope.launch {
                    googleAuthUiClient.signOut()
                    Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
                    navController.navigate("signIn"){
                        popUpTo("signIn"){ inclusive = true }
                    }
                }
            },
                navController,
                ChatState(),
                userData = googleAuthUiClient.getSignedInUser()
            )
        }
    }
}
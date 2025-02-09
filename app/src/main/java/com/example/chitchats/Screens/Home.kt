package com.example.chitchats.Screens


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chitchats.Authentication.Data
import com.example.chitchats.Authentication.GoogleAuthUiClient
import com.example.chitchats.ChatBackend.ChatState
import com.example.chitchats.ChatBackend.HomeViewModel
import com.google.android.gms.auth.api.identity.Identity

@Composable
fun Home(
    onSignOutClick: () -> Unit,
    navController: NavController,
    state: ChatState,
    userData: Data?
){
    val viewModel = hiltViewModel<HomeViewModel>()
    val channels = viewModel.channels.collectAsState()
    var showDialogue by remember { mutableStateOf(!state.isRegistered) }
    var id = userData?.id
    val context = LocalContext.current
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    var userName by remember { mutableStateOf("") }
    LaunchedEffect(Unit){
        viewModel.channelExists(id.toString()){ it ->
            if(it){
                showDialogue = false
            }
        }
        if(!state.isRegistered){
            showDialogue = true
            Log.e("Registration", "$id is registered")
        }
    }

    if (showDialogue){
        AlertDialog(
            onDismissRequest = {  },
            title = { Text("Registration")},
            text = {
                Column(){
                    Text("Enter Username: ",
                        fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text("Rules : \n1. Don't use special symbols like '@', '*', '_'.\n2. Don't use empty spaces.\n3. Your username is visible to your friends so they can connect with you.\n4. Username is not your user id.")
                    Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(userName,
                            onValueChange = { userName = it},
                            placeholder = { Text("Username")})

                }
            },

            confirmButton = {
                TextButton(onClick = {
                            googleAuthUiClient.getSignedInUser()?.let {
                                viewModel.registerYourself(id.toString(), userData = it) }
                            viewModel.onRegistered()
                            showDialogue = false
                }) {
                    Text("Register")
                }
            }
        )
    }
    Button(onSignOutClick) {
        Text("Sign Out")
    }
}
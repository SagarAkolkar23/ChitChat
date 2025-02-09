package com.example.chitchats.ChatBackend

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.chitchats.Authentication.Data
import com.example.chitchats.Authentication.signInData
import com.example.chitchats.Model.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val database = Firebase.database
    val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()
    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    fun  onRegistered(){
        _state.update { it.copy(
            isRegistered = true
        )
        }
    }

    fun channelExists(id : String, onResult: (Boolean) -> Unit) {
        database.getReference("channel").get().addOnSuccessListener {
            onResult(it.exists())
        }.addOnFailureListener{
            onResult(false)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun registerYourself(id : String, userData: Data){
        val key = database.getReference("channel").child(id)
        key.setValue(userData.name).addOnSuccessListener{
            Log.e("Registration", "$id is registered with name ${userData.name}")
        }
            .addOnFailureListener{ exception ->
                Log.e("Registration", "$exception")
            }
    }
}
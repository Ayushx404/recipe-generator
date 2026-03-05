package com.ayush.recipegenerator.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import com.ayush.recipegenerator.common.util.ConnectivityObserver
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).await()
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.localizedMessage ?: "Google Sign-In failed")
            }
        }
    }

    fun onLoginClicked(onProceed: () -> Unit) {
        if (connectivityObserver.isOnline()) {
            onProceed()
        } else {
            _authState.value = AuthState.Error("No internet connection")
        }
    }
    
    fun setErrorMessage(message: String) {
        _authState.value = AuthState.Error(message)
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
    
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
    }
}

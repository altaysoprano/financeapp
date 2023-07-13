package com.example.financeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financeapp.data.model.Transaction
import com.example.financeapp.data.repository.TransactionRepository
import com.example.financeapp.util.UiState
import com.example.financeapp.util.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: TransactionRepository
): ViewModel() {

    private val _signInState = MutableLiveData<UiState<String>>()
    val signInState: LiveData<UiState<String>>
        get() = _signInState

    fun signIn(email: String, password: String) {
        _signInState.value = UiState.Loading
        repository.signIn(email, password) {
            _signInState.value = it
        }
    }

}
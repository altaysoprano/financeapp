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
class SignUpViewModel @Inject constructor(
    private val repository: TransactionRepository
): ViewModel() {

    private val _signUpState = MutableLiveData<UiState<String>>()
    val signUpState: LiveData<UiState<String>>
        get() = _signUpState

    fun signUp(email: String, password: String) {
        _signUpState.value = UiState.Loading
        repository.signUp(email, password) {
            _signUpState.value = it
        }
    }

}
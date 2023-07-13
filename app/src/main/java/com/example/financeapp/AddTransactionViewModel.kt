package com.example.financeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financeapp.data.model.Transaction
import com.example.financeapp.data.repository.TransactionRepository
import com.example.financeapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
): ViewModel() {

    private val _addTransaction = MutableLiveData<UiState<String>>()
    val addTransaction: LiveData<UiState<String>>
        get() = _addTransaction

    fun addTransaction(transaction: Transaction) {
        _addTransaction.value = UiState.Loading
        repository.addTransaction(transaction) {
            _addTransaction.value = it
        }
    }

}
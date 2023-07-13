package com.example.financeapp.data.repository

import com.example.financeapp.data.model.Transaction
import com.example.financeapp.util.UiState

interface TransactionRepository {

    fun signIn(email: String, pass: String, result: (UiState<String>) -> Unit)
    fun signUp(email: String, pass: String, result: (UiState<String>) -> Unit)
    fun getTransactions(result: (UiState<List<Transaction>>) -> Unit)
    fun addTransaction(transaction: Transaction, result: (UiState<String>) -> Unit)
    fun deleteTransaction(transaction: Transaction, result: (UiState<String>) -> Unit)
}
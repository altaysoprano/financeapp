package com.example.financeapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financeapp.data.model.Transaction
import com.example.financeapp.data.repository.CurrencyRepository
import com.example.financeapp.data.repository.TransactionRepository
import com.example.financeapp.util.BudgetState
import com.example.financeapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val curRepository: CurrencyRepository
): ViewModel() {

    private val _transactions = MutableLiveData<UiState<List<Transaction>>>()
    val transactions: LiveData<UiState<List<Transaction>>>
        get() = _transactions

    private val _budgetState = MutableLiveData<BudgetState>()
    val budgetState: LiveData<BudgetState>
        get() = _budgetState

    private val _deleteTransaction = MutableLiveData<UiState<String>>()
    val deleteTransaction: LiveData<UiState<String>>
        get() = _deleteTransaction

    private val _changeTransaction = MutableLiveData<UiState<String>>()
    val changeTransaction: LiveData<UiState<String>>
        get() = _changeTransaction

    init {
        getTransactions()
    }

    fun getTransactions() {
        _transactions.value = UiState.Loading
        repository.getTransactions {
            _transactions.value = it
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        _deleteTransaction.value = UiState.Loading
        repository.deleteTransaction(transaction) {
            _deleteTransaction.value = it
        }
        getTransactions()
    }

    suspend fun changeCurrency(base: String) {
/*
        _changeTransaction.value = UiState.Loading
        curRepository.getRates(base)
*/
    }

    fun updateStatistics(data: List<Transaction>) {
        val budgetTotal = calculateBudgetTotal(data)
        val expenseTotal = calculateExpenseTotal(data)
        val balance = budgetTotal - expenseTotal

        _budgetState.value = BudgetState(
            budget = "$%.2f".format(budgetTotal),
            expense = "$%.2f".format(expenseTotal),
            balance = "$%.2f".format(balance)
        )

        /*
                binding.budget.text = "$%.2f".format(budgetTotal)
                binding.expense.text = "$%.2f".format(expenseTotal)

                val balance = budgetTotal - expenseTotal
                binding.balance.text = "$%.2f".format(balance)
        */

    }

    private fun calculateBudgetTotal(transactions: List<Transaction>): Double {
        var budgetTotal = 0.0
        for (transaction in transactions) {
            if (transaction.amount >= 0) {
                budgetTotal += transaction.amount
            }
        }
        return budgetTotal
    }

    private fun calculateExpenseTotal(transactions: List<Transaction>): Double {
        var expenseTotal = 0.0
        for (transaction in transactions) {
            if (transaction.amount < 0) {
                expenseTotal += transaction.amount
            }
        }
        return Math.abs(expenseTotal)
    }

}
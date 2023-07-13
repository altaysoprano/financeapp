package com.example.financeapp.util

sealed class UserState<out T> {
    object Loading: UserState<Nothing>()
    object Success: UserState<Nothing>()
    data class Failure(val error: String?): UserState<Nothing>()
}

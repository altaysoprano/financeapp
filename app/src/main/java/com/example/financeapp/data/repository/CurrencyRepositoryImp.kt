package com.example.financeapp.data.repository

import com.example.financeapp.data.model.CurrencyResponse
import com.example.financeapp.util.UiState
import javax.inject.Inject

class CurrencyRepositoryImp @Inject constructor(
    private val api: CurrencyApi) : CurrencyRepository {
/*
    override suspend fun getRates(base: String, result: (UiState<String>) -> Unit) {
*/
/*
        return try {
            val response = api.getRates(base)
            val data = response.body()
            if(response.isSuccessful && result != null) {
                result.invoke(
                    UiState.Success(data)
                )
            } else {
                result.invoke(
                    UiState.Failure(response.message())
                )
            }
        } catch(e: Exception) {
            result.invoke(
                UiState.Failure(e.message ?: "An error occured")
            )
        }
*//*

    }

*/
}
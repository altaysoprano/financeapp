package com.example.financeapp.data.repository

import com.example.financeapp.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/latest")
    suspend fun getRates(
        @Query("base_currency") base: String,
    ): Response<CurrencyResponse>

}
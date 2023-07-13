package com.example.financeapp.di

import com.example.financeapp.data.repository.CurrencyApi
import com.example.financeapp.data.repository.CurrencyRepository
import com.example.financeapp.data.repository.CurrencyRepositoryImp
import com.example.financeapp.data.repository.TransactionRepository
import com.example.financeapp.data.repository.TransactionRepositoryImp
import com.example.financeapp.util.FirestoreTables.BASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTransactionRepository(
        firebaseAuth: FirebaseAuth,
        database: FirebaseFirestore
    ) : TransactionRepository {
        return TransactionRepositoryImp(firebaseAuth, database)
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(): CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    @Provides
    @Singleton
    fun provideCurrencyRepository(api: CurrencyApi): CurrencyRepository = CurrencyRepositoryImp(api)



}
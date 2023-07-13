package com.example.financeapp.data.repository

import android.content.Intent
import android.widget.Toast
import com.example.financeapp.MainActivity
import com.example.financeapp.data.model.Transaction
import com.example.financeapp.data.model.User
import com.example.financeapp.util.FirestoreTables
import com.example.financeapp.util.UiState
import com.example.financeapp.util.UserState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TransactionRepositoryImp(
    val firebaseAuth: FirebaseAuth,
    val database: FirebaseFirestore
) : TransactionRepository {

    override fun signIn(email: String, pass: String, result: (UiState<String>) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                result.invoke(
                    UiState.Success(it.result.toString())
                )
            } else {
                result.invoke(
                    UiState.Failure("Kullanıcı yok veya şifre hatalı")
                )
            }
        }
    }

    override fun getTransactions(result: (UiState<List<Transaction>>) -> Unit) {
        val uid = firebaseAuth.currentUser?.uid
        database.collection(FirestoreTables.USER)
            .document(uid ?: "")
            .collection(FirestoreTables.TRANSACTION)
            .get()
            .addOnSuccessListener {
                val transactions = arrayListOf<Transaction>()
                for (document in it) {
                    val transaction = document.toObject(Transaction::class.java)
                    transactions.add(transaction)
                }
                result.invoke(
                    UiState.Success(transactions)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    (
                            UiState.Failure(
                                it.localizedMessage
                            )
                            )
                )
            }
    }

    override fun addTransaction(transaction: Transaction, result: (UiState<String>) -> Unit) {
        val uid = firebaseAuth.currentUser?.uid
        val document = database.collection(FirestoreTables.USER)
            .document(uid?: "")
            .collection(FirestoreTables.TRANSACTION)
            .document()
        transaction.id = document.id
        document.set(transaction)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Transaction added")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun signUp(email: String, pass: String, result: (UiState<String>) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                database.collection("user").add(User(email, pass))
                    .addOnSuccessListener { result.invoke(UiState.Success(it.id)) }
                    .addOnFailureListener { result.invoke((UiState.Failure(it.localizedMessage))) }
            } else {
                result.invoke(UiState.Failure("Kullanıcı zaten var veya farklı bir hata oluştu"))
            }
        }
    }

    override fun deleteTransaction(transaction: Transaction, result: (UiState<String>) -> Unit) {
        val uid = firebaseAuth.currentUser?.uid
        database.collection(FirestoreTables.USER)
            .document(uid ?: "")
            .collection(FirestoreTables.TRANSACTION)
            .document(transaction.id)
            .delete()
            .addOnSuccessListener {
                result.invoke(UiState.Success("Transaction successfully deleted!"))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure(e.message))
            }
    }
}
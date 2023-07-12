package com.example.financeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.financeapp.databinding.ActivitySignUpBinding
import com.example.financeapp.util.UiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: SignUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding  = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if(pass == confirmPass) {
                    viewModel.signUp(email, pass)
/*
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if(it.isSuccessful) {
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
*/
                } else {
                    Toast.makeText(this, "Şifreler uyuşmuyor", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Email, şifre veya onay şifresi boş bırakılamaz !", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.signUpState.observe(this) { state ->
            when(state) {
                is UiState.Loading -> {
                    binding.progressBarSignIn.visibility = View.VISIBLE
                }
                is UiState.Failure -> {
                    binding.progressBarSignIn.visibility = View.GONE
                    Toast.makeText(this, state.error.toString(), Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    binding.progressBarSignIn.visibility = View.GONE
                    Toast.makeText(this, "Successfully signed up", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                else -> {

                }
            }
        }

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
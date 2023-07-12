package com.example.financeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.example.financeapp.databinding.ActivityAddTransactionBinding

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addTransactionBtn.setOnClickListener {
            val label = binding.labelInput.text.toString()
            val amount = binding.amountInput.text.toString().toDoubleOrNull()

            binding.labelInput.addTextChangedListener {
                if(it!!.count() > 0)
                    binding.labelLayout.error = null
            }

            if(label.isEmpty())
                binding.labelLayout.error = "Please enter a valid label"

            if(amount == null)
                binding.amountLayout.error = "Please enter a valied label"
        }

        binding.closeBtn.setOnClickListener {
            finish()
        }
    }
}
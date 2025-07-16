package com.example.s12_calculadora

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CalculatorViewModel
    private lateinit var displayTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the ViewModel. ViewModelProvider ensures the ViewModel
        // survives configuration changes (e.g., screen rotation).
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)

        // Get reference to the display TextView from the layout.
        displayTextView = findViewById(R.id.displayTextView)

        // Observe the displayText LiveData from the ViewModel.
        // Whenever displayText changes in the ViewModel, the UI will be updated.
        viewModel.displayText.observe(this) { text ->
            displayTextView.text = text
        }

        // Set up click listeners for all number buttons
        findViewById<MaterialButton>(R.id.button_0).setOnClickListener { viewModel.onDigitClick("0") }
        findViewById<MaterialButton>(R.id.button_1).setOnClickListener { viewModel.onDigitClick("1") }
        findViewById<MaterialButton>(R.id.button_2).setOnClickListener { viewModel.onDigitClick("2") }
        findViewById<MaterialButton>(R.id.button_3).setOnClickListener { viewModel.onDigitClick("3") }
        findViewById<MaterialButton>(R.id.button_4).setOnClickListener { viewModel.onDigitClick("4") }
        findViewById<MaterialButton>(R.id.button_5).setOnClickListener { viewModel.onDigitClick("5") }
        findViewById<MaterialButton>(R.id.button_6).setOnClickListener { viewModel.onDigitClick("6") }
        findViewById<MaterialButton>(R.id.button_7).setOnClickListener { viewModel.onDigitClick("7") }
        findViewById<MaterialButton>(R.id.button_8).setOnClickListener { viewModel.onDigitClick("8") }
        findViewById<MaterialButton>(R.id.button_9).setOnClickListener { viewModel.onDigitClick("9") }
        findViewById<MaterialButton>(R.id.button_decimal).setOnClickListener { viewModel.onDigitClick(".") }

        // Set up click listeners for all operator buttons
        findViewById<MaterialButton>(R.id.button_add).setOnClickListener { viewModel.onOperatorClick("+") }
        findViewById<MaterialButton>(R.id.button_subtract).setOnClickListener { viewModel.onOperatorClick("-") }
        findViewById<MaterialButton>(R.id.button_multiply).setOnClickListener { viewModel.onOperatorClick("×") }
        findViewById<MaterialButton>(R.id.button_divide).setOnClickListener { viewModel.onOperatorClick("÷") }
        findViewById<MaterialButton>(R.id.button_power).setOnClickListener { viewModel.onOperatorClick("^") }
        findViewById<MaterialButton>(R.id.button_sqrt).setOnClickListener { viewModel.onSquareRootClick() }

        // Set up click listener for the equals button
        findViewById<MaterialButton>(R.id.button_equals).setOnClickListener { viewModel.onEqualsClick() }

        // Set up click listener for the clear (AC) button
        findViewById<MaterialButton>(R.id.button_ac).setOnClickListener { viewModel.onClearClick() }
    }
}

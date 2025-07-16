package com.example.s12_calculadora
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.pow
import kotlin.math.sqrt

class CalculatorViewModel : ViewModel() {

    // MutableLiveData to hold the current display text of the calculator.
    // This will be observed by the UI.
    private val _displayText = MutableLiveData<String>()
    val displayText: LiveData<String> = _displayText

    // Variables to store the current input, the first operand, and the operator.
    private var currentInput: String = ""
    private var firstOperand: Double = 0.0
    private var currentOperator: String? = null
    private var isNewOperation: Boolean = true // Flag to indicate if a new operation is starting

    init {
        // Initialize the display text when the ViewModel is created.
        _displayText.value = "0"
    }

    /**
     * Appends a digit to the current input string.
     * Handles cases like leading zeros and multiple decimal points.
     */
    fun onDigitClick(digit: String) {
        if (isNewOperation) {
            currentInput = digit
            isNewOperation = false
        } else {
            if (currentInput == "0" && digit != ".") {
                currentInput = digit
            } else if (digit == "." && currentInput.contains(".")) {
                // Do nothing if decimal already exists
            } else {
                currentInput += digit
            }
        }
        _displayText.value = currentInput
    }

    /**
     * Sets the current operator and stores the first operand.
     * If an operator is already set, it performs the previous calculation first.
     */
    fun onOperatorClick(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (currentOperator != null && !isNewOperation) {
                // If there's an existing operation, calculate the result first
                calculateResult()
                firstOperand = _displayText.value?.toDoubleOrNull() ?: 0.0
            } else {
                firstOperand = currentInput.toDoubleOrNull() ?: 0.0
            }
            currentOperator = operator
            isNewOperation = true // Ready for the next number input
        } else if (operator == "-" && currentInput.isEmpty() && isNewOperation) {
            // Allow negative sign at the beginning of a new number
            currentInput = "-"
            _displayText.value = currentInput
            isNewOperation = false
        }
    }

    /**
     * Performs the calculation based on the current operator and operands.
     * Updates the display with the result.
     */
    fun onEqualsClick() {
        if (currentInput.isNotEmpty() && currentOperator != null) {
            calculateResult()
            currentOperator = null // Reset operator after calculation
            isNewOperation = true // Ready for a completely new operation
        }
    }

    /**
     * Clears all calculator state, resetting it to initial values.
     */
    fun onClearClick() {
        currentInput = ""
        firstOperand = 0.0
        currentOperator = null
        isNewOperation = true
        _displayText.value = "0"
    }

    /**
     * Handles the square root operation.
     */
    fun onSquareRootClick() {
        val value = currentInput.toDoubleOrNull() ?: _displayText.value?.toDoubleOrNull()
        if (value != null && value >= 0) {
            val result = sqrt(value)
            _displayText.value = formatResult(result)
            currentInput = _displayText.value.toString()
            isNewOperation = true
        } else {
            _displayText.value = "Error" // Or handle negative square root differently
            currentInput = ""
            isNewOperation = true
        }
    }

    /**
     * Helper function to perform the actual calculation.
     */
    private fun calculateResult() {
        val secondOperand = currentInput.toDoubleOrNull() ?: 0.0
        val result = when (currentOperator) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "×" -> firstOperand * secondOperand
            "÷" -> {
                if (secondOperand != 0.0) firstOperand / secondOperand else Double.NaN // Handle division by zero
            }
            "^" -> firstOperand.pow(secondOperand)
            else -> secondOperand // If no operator, just display the second operand
        }

        if (result.isNaN()) {
            _displayText.value = "Error"
        } else {
            _displayText.value = formatResult(result)
        }
        currentInput = _displayText.value.toString() // Set current input to result for chaining operations
    }

    /**
     * Formats the result to avoid unnecessary decimal points if it's an integer.
     */
    private fun formatResult(result: Double): String {
        return if (result == result.toLong().toDouble()) {
            result.toLong().toString()
        } else {
            String.format("%.8f", result).trimEnd('0').trimEnd('.') // Limit decimal places and remove trailing zeros
        }
    }
}
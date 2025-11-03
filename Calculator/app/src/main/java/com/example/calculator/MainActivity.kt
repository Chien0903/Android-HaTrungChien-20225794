package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView

    private var currentOperand: String = ""
    private var previousOperand: String = ""
    private var operation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator)

        expressionTextView = findViewById(R.id.expression)
        resultTextView = findViewById(R.id.result)

        val numberButtons = listOf<Button>(
            findViewById(R.id.zero),
            findViewById(R.id.one),
            findViewById(R.id.two),
            findViewById(R.id.three),
            findViewById(R.id.four),
            findViewById(R.id.five),
            findViewById(R.id.six),
            findViewById(R.id.seven),
            findViewById(R.id.eight),
            findViewById(R.id.nine),
            findViewById(R.id.dot)
        )

        val operationButtons = listOf<Button>(
            findViewById(R.id.add),
            findViewById(R.id.substract),
            findViewById(R.id.multiply),
            findViewById(R.id.divide)
        )

        val controlButtons = mapOf<Button, () -> Unit>(
            findViewById<Button>(R.id.C) to ::clear,
            findViewById<Button>(R.id.CE) to ::clearEntry,
            findViewById<Button>(R.id.BS) to ::backspace,
            findViewById<Button>(R.id.equal) to ::calculate
        )

        numberButtons.forEach { button ->
            button.setOnClickListener { onNumberClick(button.text.toString()) }
        }

        operationButtons.forEach { button ->
            button.setOnClickListener { onOperationClick(button.text.toString()) }
        }

        controlButtons.forEach { (button, action) ->
            button.setOnClickListener { action() }
        }
    }

    private fun onNumberClick(number: String) {
        currentOperand += number
        resultTextView.text = currentOperand
        updateExpression()
    }

    private fun onOperationClick(op: String) {
        if (currentOperand.isNotEmpty()) {
            previousOperand = currentOperand
            currentOperand = ""
        }
        operation = op
        updateExpression()
    }

    private fun calculate() {
        if (previousOperand.isNotEmpty() && currentOperand.isNotEmpty() && operation.isNotEmpty()) {
            val result = when (operation) {
                "+" -> previousOperand.toInt() + currentOperand.toInt()
                "-" -> previousOperand.toInt() - currentOperand.toInt()
                "x" -> previousOperand.toInt() * currentOperand.toInt()
                "/" -> {
                    if (currentOperand.toInt() != 0) {
                        previousOperand.toInt() / currentOperand.toInt()
                    } else {
                        "Error"
                    }
                }
                else -> ""
            }
            resultTextView.text = result.toString()
            currentOperand = result.toString()
            previousOperand = ""
            operation = ""
            expressionTextView.text = ""
        }
    }

    private fun clear() {
        currentOperand = ""
        previousOperand = ""
        operation = ""
        updateExpression()
        resultTextView.text = "0"
    }

    private fun clearEntry() {
        currentOperand = ""
        updateExpression()
        resultTextView.text = "0"
    }

    private fun backspace() {
        if (currentOperand.isNotEmpty()) {
            currentOperand = currentOperand.substring(0, currentOperand.length - 1)
            resultTextView.text = if (currentOperand.isNotEmpty()) currentOperand else "0"
            updateExpression()
        }
    }

    private fun updateExpression() {
        expressionTextView.text = "$previousOperand $operation $currentOperand"
    }
}
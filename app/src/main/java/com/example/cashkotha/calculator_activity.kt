package com.example.cashkotha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout

class CalculatorActivity : AppCompatActivity() {

    // VAT Calculator Views
    private lateinit var vatAmountInput: EditText
    private lateinit var vatPercentageInput: EditText
    private lateinit var calculateVatBtn: Button
    private lateinit var vatResultCard: MaterialCardView
    private lateinit var vatAmountText: TextView
    private lateinit var totalWithVatText: TextView

    // Discount Calculator Views
    private lateinit var purchaseAmountInput: EditText
    private lateinit var discountPercentageInput: EditText
    private lateinit var maxDiscountInput: EditText
    private lateinit var calculateDiscountBtn: Button
    private lateinit var discountResultCard: MaterialCardView
    private lateinit var discountAmountText: TextView
    private lateinit var finalAmountText: TextView
    private lateinit var savedAmountText: TextView

    // Cashback Calculator Views
    private lateinit var cashbackPurchaseInput: EditText
    private lateinit var cashbackPercentageInput: EditText
    private lateinit var maxCashbackInput: EditText
    private lateinit var calculateCashbackBtn: Button
    private lateinit var cashbackResultCard: MaterialCardView
    private lateinit var cashbackAmountText: TextView
    private lateinit var cashbackFinalAmountText: TextView

    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        // Initialize views
        initializeViews()

        // Set up tab layout
        setupTabLayout()

        // Set default tab
        showVatCalculator()
    }

    private fun initializeViews() {
        tabLayout = findViewById(R.id.tabLayout)

        // VAT Calculator
        vatAmountInput = findViewById(R.id.vatAmountInput)
        vatPercentageInput = findViewById(R.id.vatPercentageInput)
        calculateVatBtn = findViewById(R.id.calculateVatBtn)
        vatResultCard = findViewById(R.id.vatResultCard)
        vatAmountText = findViewById(R.id.vatAmountText)
        totalWithVatText = findViewById(R.id.totalWithVatText)

        // Discount Calculator
        purchaseAmountInput = findViewById(R.id.purchaseAmountInput)
        discountPercentageInput = findViewById(R.id.discountPercentageInput)
        maxDiscountInput = findViewById(R.id.maxDiscountInput)
        calculateDiscountBtn = findViewById(R.id.calculateDiscountBtn)
        discountResultCard = findViewById(R.id.discountResultCard)
        discountAmountText = findViewById(R.id.discountAmountText)
        finalAmountText = findViewById(R.id.finalAmountText)
        savedAmountText = findViewById(R.id.savedAmountText)

        // Cashback Calculator
        cashbackPurchaseInput = findViewById(R.id.cashbackPurchaseInput)
        cashbackPercentageInput = findViewById(R.id.cashbackPercentageInput)
        maxCashbackInput = findViewById(R.id.maxCashbackInput)
        calculateCashbackBtn = findViewById(R.id.calculateCashbackBtn)
        cashbackResultCard = findViewById(R.id.cashbackResultCard)
        cashbackAmountText = findViewById(R.id.cashbackAmountText)
        cashbackFinalAmountText = findViewById(R.id.cashbackFinalAmountText)

        // Set click listeners
        calculateVatBtn.setOnClickListener { calculateVAT() }
        calculateDiscountBtn.setOnClickListener { calculateDiscount() }
        calculateCashbackBtn.setOnClickListener { calculateCashback() }
    }

    private fun setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("VAT"))
        tabLayout.addTab(tabLayout.newTab().setText("Discount"))
        tabLayout.addTab(tabLayout.newTab().setText("Cashback"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showVatCalculator()
                    1 -> showDiscountCalculator()
                    2 -> showCashbackCalculator()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun showVatCalculator() {
        findViewById<MaterialCardView>(R.id.vatCalculatorCard).visibility = android.view.View.VISIBLE
        findViewById<MaterialCardView>(R.id.discountCalculatorCard).visibility = android.view.View.GONE
        findViewById<MaterialCardView>(R.id.cashbackCalculatorCard).visibility = android.view.View.GONE
    }

    private fun showDiscountCalculator() {
        findViewById<MaterialCardView>(R.id.vatCalculatorCard).visibility = android.view.View.GONE
        findViewById<MaterialCardView>(R.id.discountCalculatorCard).visibility = android.view.View.VISIBLE
        findViewById<MaterialCardView>(R.id.cashbackCalculatorCard).visibility = android.view.View.GONE
    }

    private fun showCashbackCalculator() {
        findViewById<MaterialCardView>(R.id.vatCalculatorCard).visibility = android.view.View.GONE
        findViewById<MaterialCardView>(R.id.discountCalculatorCard).visibility = android.view.View.GONE
        findViewById<MaterialCardView>(R.id.cashbackCalculatorCard).visibility = android.view.View.VISIBLE
    }

    private fun calculateVAT() {
        val amountStr = vatAmountInput.text.toString()
        val percentageStr = vatPercentageInput.text.toString()

        if (amountStr.isEmpty() || percentageStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        val percentage = percentageStr.toDoubleOrNull()

        if (amount == null || percentage == null || amount < 0 || percentage < 0) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        val vatAmount = (amount * percentage) / 100
        val totalWithVat = amount + vatAmount

        vatResultCard.visibility = android.view.View.VISIBLE
        vatAmountText.text = "VAT Amount: ৳${String.format("%.2f", vatAmount)}"
        totalWithVatText.text = "Total with VAT: ৳${String.format("%.2f", totalWithVat)}"
    }

    private fun calculateDiscount() {
        val amountStr = purchaseAmountInput.text.toString()
        val percentageStr = discountPercentageInput.text.toString()
        val maxDiscountStr = maxDiscountInput.text.toString()

        if (amountStr.isEmpty() || percentageStr.isEmpty()) {
            Toast.makeText(this, "Please fill purchase amount and discount %", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        val percentage = percentageStr.toDoubleOrNull()
        val maxDiscount = if (maxDiscountStr.isEmpty()) Double.MAX_VALUE else maxDiscountStr.toDoubleOrNull() ?: Double.MAX_VALUE

        if (amount == null || percentage == null || amount < 0 || percentage < 0) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        var discountAmount = (amount * percentage) / 100

        // Apply max discount cap
        if (discountAmount > maxDiscount) {
            discountAmount = maxDiscount
        }

        val finalAmount = amount - discountAmount

        discountResultCard.visibility = android.view.View.VISIBLE
        discountAmountText.text = "Discount: ৳${String.format("%.2f", discountAmount)}"
        finalAmountText.text = "Final Amount: ৳${String.format("%.2f", finalAmount)}"
        savedAmountText.text = "You Saved: ৳${String.format("%.2f", discountAmount)}"
    }

    private fun calculateCashback() {
        val amountStr = cashbackPurchaseInput.text.toString()
        val percentageStr = cashbackPercentageInput.text.toString()
        val maxCashbackStr = maxCashbackInput.text.toString()

        if (amountStr.isEmpty() || percentageStr.isEmpty()) {
            Toast.makeText(this, "Please fill purchase amount and cashback %", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        val percentage = percentageStr.toDoubleOrNull()
        val maxCashback = if (maxCashbackStr.isEmpty()) Double.MAX_VALUE else maxCashbackStr.toDoubleOrNull() ?: Double.MAX_VALUE

        if (amount == null || percentage == null || amount < 0 || percentage < 0) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        var cashbackAmount = (amount * percentage) / 100

        // Apply max cashback cap
        if (cashbackAmount > maxCashback) {
            cashbackAmount = maxCashback
        }

        cashbackResultCard.visibility = android.view.View.VISIBLE
        cashbackAmountText.text = "Cashback: ৳${String.format("%.2f", cashbackAmount)}"
        cashbackFinalAmountText.text = "You Pay: ৳${String.format("%.2f", amount)}\nYou Get Back: ৳${String.format("%.2f", cashbackAmount)}"
    }
}
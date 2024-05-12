package com.nicholostyler.composetipcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class TipViewModel : ViewModel() {
    private var _isCents = false
    private var tipPercent = 0.00

    val isCents: Boolean
        get() = _isCents

    var billTotal by mutableDoubleStateOf( 0.0)
        private set

    var selectedTipPercentage by mutableIntStateOf(3)
        private set

    var splitBy by mutableIntStateOf(2)
        private set

    var perPersonAmount by mutableDoubleStateOf(0.00)
        private set

    var taxTotal by mutableDoubleStateOf(0.0)
        private set

    var totalWithTip by mutableDoubleStateOf(0.0)
        private set

    fun calculatePerAmount()
    {
        // update values on first launch
        updateSelectedTipPercentage(selectedTipPercentage)
        taxTotal = billTotal * tipPercent
        totalWithTip = billTotal + taxTotal
        perPersonAmount = (totalWithTip / splitBy)

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        var billFormat = df.format(perPersonAmount)
        perPersonAmount = billFormat.toDouble()

        billFormat = df.format(taxTotal)
        taxTotal = billFormat.toDouble()

        billFormat = df.format(totalWithTip)
        totalWithTip = billFormat.toDouble()
    }

    fun updateSelectedTipPercentage(newValue: Int) {
        if (newValue >= 5) return
        when (newValue)
        {
            0 -> updateTipPercentage(0.10)
            1 -> updateTipPercentage(0.15)
            2 -> updateTipPercentage(0.18)
            3 -> updateTipPercentage(0.20)
        }
        selectedTipPercentage = newValue
    }

    fun updateTipPercentage(newValue: Double) {
        // add error handling
        tipPercent = newValue
    }

    fun updateSplitBy(isIncrement: Boolean) {
        var newValue = splitBy
        if (isIncrement)
        {
            newValue++
        }
        else
        {
            newValue--
        }

        // If the decremented value is zero or less.
        if (newValue <= 0) return

        splitBy = newValue
    }

    fun updateBillTotal(newNumber: String)
    {
        if (newNumber == ".")
        {
            updateIsCents()
            return
        }
        else if (newNumber == "x")
        {
            deleteButtonClick()
            return
        }

        //TODO add error handling
        val label = billTotal.toString()
        val newLabel: String
        val split = label.split(".")
        val beforeCents = split[0]
        val afterCents = split[1]
        if (isCents)
        {
            if (afterCents.length == 2) return
            if (afterCents == "0")
                newLabel = beforeCents + "." + newNumber
            else
                newLabel = beforeCents + "." + afterCents + newNumber
        }
        else
        {
            if (beforeCents == "0") newLabel = newNumber + ".00"
            else newLabel = beforeCents + newNumber + ".00"
        }

        val newBalance = newLabel.toDouble()
        billTotal = newBalance
    }

    fun updateIsCents()
    {
        if (isCents) return;
        else {
            _isCents = true
        }
    }

    fun deleteButtonClick()
    {
        val subtotal = billTotal.toString()
        var deletedSubtotal: String = ""

        if (isCents)
        {
            deletedSubtotal = subtotal.substring(0, subtotal.length - 1)
            if (deletedSubtotal[deletedSubtotal.length - 1] == '.')
            {
                // change isCents to false
                _isCents = false
                deletedSubtotal = subtotal.substring(0, subtotal.length - 1)
            }
        }
        else
        {
            if (!isCents)
            {
                val splitTotalString = subtotal.split(".")
                val beforeCents = splitTotalString[0]
                if (beforeCents.length == 1)
                {
                    // reset to 0.00
                    deletedSubtotal = "0.00"
                }
                else
                {
                    deletedSubtotal = beforeCents.substring(0, beforeCents.length - 1)
                }
            }
        }

        //TODO: Add error handling
        billTotal = deletedSubtotal.toDouble()
        calculatePerAmount()
    }

}
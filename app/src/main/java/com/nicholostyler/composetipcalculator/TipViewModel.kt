package com.nicholostyler.composetipcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TipViewModel : ViewModel() {
    private var _isCents = false
    private var tipPercent = 0.00
    private var _count = 0

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

    fun calculatePerAmount()
    {
        // update values on first launch
        updateSelectedTipPercentage(selectedTipPercentage)
        var billWithTip = billTotal + (billTotal * tipPercent)
        perPersonAmount = billWithTip / splitBy
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

        //TODO add error handling
        var label = billTotal.toString()
        var newLabel = ""
        var split = label.split(".")
        var beforeCents = split[0]
        var afterCents = split[1]
        if (isCents)
        {
            if (afterCents.length == 2) return
            newLabel = beforeCents + "." + newNumber
        }
        else
        {
            if (beforeCents == "0") newLabel = newNumber + ".00"
            else newLabel = beforeCents + newNumber + ".00"
        }

        var newBalance = newLabel.toDouble()
        billTotal = newBalance
    }

    fun updateIsCents()
    {
        if (isCents) return;
        else {
            _isCents = true
        }
    }

}
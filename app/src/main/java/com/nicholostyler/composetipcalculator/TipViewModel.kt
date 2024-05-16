package com.nicholostyler.composetipcalculator

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class TipViewModel : ViewModel() {
    private var _isCents = false
    private var _segmentedButtonList = listOf("10%", "15%", "18%", "20%", "Custom")
    private var tipPercent = 0.00
    private val _openCustomDisplay = mutableStateOf(false)

    val isCents: Boolean
        get() = _isCents

    var segmentedButtonsList: List<String> = listOf("10%", "15%", "18%", "20%", "Custom")
        get() = _segmentedButtonList

    var segmentedButtonCount by mutableIntStateOf(2)
        private set

    var billTotal by mutableDoubleStateOf( 0.0)
        private set

    var selectedTipPercentage by mutableIntStateOf(20)
        private set

    var splitBy by mutableIntStateOf(2)
        private set

    var perPersonAmount by mutableDoubleStateOf(0.00)
        private set

    var taxTotal by mutableDoubleStateOf(0.0)
        private set

    var totalWithTip by mutableDoubleStateOf(0.0)
        private set

    var selectedSegmentedTip by mutableIntStateOf(0)
        private set

    var tipTotalWithSplit by mutableDoubleStateOf(0.0)
        private set

    var openCustomDisplay: State<Boolean> = _openCustomDisplay

    init
    {
        selectedSegmentedTip = 0
        tipPercent = .2
    }

    fun changeSegmentedButtonCount(buttonCount: Int)
    {
        if (buttonCount <= 0) return;
        if (buttonCount >= 5) return;

        when (buttonCount)
        {
            2 -> {
                _segmentedButtonList = listOf("20%", "Custom")
            }
            3 -> {
                _segmentedButtonList = listOf("18%", "20%", "Custom")
            }
            4 ->
            {
                _segmentedButtonList = listOf("15%", "18%", "20%", "Custom")
            }
            5 ->
            {
                _segmentedButtonList = listOf("10%", "15%", "18%", "20%", "Custom")
            }

        }

        //segmentedButtonCount = buttonCount
    }


    fun changeCustomDisplay()
    {
        _openCustomDisplay.value = !_openCustomDisplay.value
    }

    fun calculatePerAmount()
    {
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

        tipTotalWithSplit = taxTotal / splitBy
    }

    fun calculatePerAmount(newPercent: Int) : Double
    {
        val newTipPercent = newPercent.toDouble() / 100
        var newTaxTotal = billTotal * newTipPercent
        var newTotalWithTip = billTotal + newTaxTotal
        return newTotalWithTip
    }

    fun updateSelectedTipPercentage(index: Int, newValue: String) {

        // Convert to double and convert to percent
        // Cut % sign from end of string

        if (newValue == "Custom") {
            selectedSegmentedTip = _segmentedButtonList.size - 1
        }

        val newValue = newValue.substring(0, newValue.length - 1)
        selectedTipPercentage = newValue.toInt()

        val doubleValue: Double = (newValue.toDouble() * 1/100)

        updateTipPercentage(doubleValue)
        selectedSegmentedTip = index
    }

    //TODO: Make this an overloaded method instead
    fun updateSelectedPercentage(newValue: Int) {
        var doubleValue: Double = (newValue.toDouble() * 1/100)
        tipPercent = doubleValue
        selectedTipPercentage = newValue
        selectedSegmentedTip = _segmentedButtonList.indexOf("Custom")
        calculatePerAmount()
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

    fun updateBillTotal(newPercent: Double)
    {

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

    fun toClipboard(): String
    {
        return "test"
    }

}
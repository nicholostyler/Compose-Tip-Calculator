package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nicholostyler.composetipcalculator.TipViewModel
import java.text.NumberFormat
import java.util.Currency

@Composable
fun PercentCardsList(
    modifier: Modifier = Modifier.fillMaxSize(), tipViewModel: TipViewModel
)
{
    // Create list of percent values
    var percentArray = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)
    // Create list of cards
    LazyColumn(modifier = modifier.fillMaxWidth()){
        items(percentArray) { percent ->
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.setMaximumFractionDigits(2)
            format.setCurrency(Currency.getInstance("USD"))

            val total = format.format(tipViewModel.calculatePerAmount(percent))
            TipCard(percent, total, tipViewModel)
        }
    }
}
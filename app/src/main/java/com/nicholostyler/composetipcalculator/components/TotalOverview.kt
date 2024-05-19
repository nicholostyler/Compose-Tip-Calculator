package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicholostyler.composetipcalculator.TipViewModel
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
fun TotalOverview(modifier: Modifier, tipViewModel: TipViewModel)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.fillMaxHeight()
            .then(modifier)
    ) {
        TotalCard(modifier = Modifier.weight(1f), tipViewModel = tipViewModel)
        TipCard(modifier = Modifier.weight(1f), tipViewModel = tipViewModel)

    }
}

@Composable
fun TipCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
{
    // Create a NumberFormat instance
    // with US currency and 2 decimal places
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance(Locale.getDefault())
    val tipTotal = format.format(tipViewModel.taxTotal)
    val tipPerPerson = format.format(tipViewModel.tipTotalWithSplit)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .then(modifier)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .weight(1f)
        ){
            Text("Tip Total ${tipViewModel.selectedTipPercentage}%", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = tipTotal)
            Text("Tip Per Person", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            Text(text = tipPerPerson)
        }
    }
}

@Composable
fun TotalCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
{
    // Create a NumberFormat instance
    // with US currency and 2 decimal places
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance(Locale.getDefault())
    val billTotal = format.format(tipViewModel.billTotal)
    val totalWithTip = format.format(tipViewModel.totalWithTip)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .then(modifier)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .weight(1f)
        ) {
            Text("Bill Total", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = billTotal)
            Text("Total With Tip", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            Text(text = totalWithTip)
        }
    }
}

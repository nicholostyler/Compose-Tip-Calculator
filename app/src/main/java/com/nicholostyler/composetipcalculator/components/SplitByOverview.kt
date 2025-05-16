package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicholostyler.composetipcalculator.TipViewModel
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
fun PerPersonAmountCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
{
    // Create a NumberFormat instance
    // with US currency and 2 decimal places
    // TODO: Add option for currency selection
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance(Locale.getDefault())

    val perPerson = format.format(tipViewModel.perPersonAmount)

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

            .weight(1f)) {
            Text("Split Total", fontSize = MaterialTheme.typography.titleSmall.fontSize, fontWeight = FontWeight.Bold)
            Text(text = perPerson)
        }
    }
}

@Composable
fun SplitByCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
{
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
            .weight(1f)) {
            Text("Split By", fontSize = MaterialTheme.typography.titleSmall.fontSize, fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth()){
                Button(modifier = Modifier
                    .weight(1f)
                    .padding(end = 3.dp), onClick = {tipViewModel.updateSplitBy(false)}){Text(text = "-")}
                Text(text = tipViewModel.splitBy.toString())

                Button(modifier = Modifier
                    .weight(1f)
                    .padding(start = 3.dp), onClick = {tipViewModel.updateSplitBy(true)}){Text(text="+")}
            }
        }


    }
}

@Composable
fun SplitByOverview(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .then(modifier)
    ) {
        SplitByCard(modifier = Modifier.weight(1f), tipViewModel = tipViewModel)
        PerPersonAmountCard(modifier = Modifier.weight(1f), tipViewModel = tipViewModel)

    }
}

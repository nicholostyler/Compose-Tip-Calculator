package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicholostyler.composetipcalculator.TipViewModel
import java.text.NumberFormat
import java.util.Currency

@Composable
fun SplitByOverview(modifier: Modifier, tipViewModel: TipViewModel)
{
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.setMaximumFractionDigits(2)
    format.setCurrency(Currency.getInstance("USD"))

    val convertedPerPerson = format.format(tipViewModel.perPersonAmount)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            //.height(100.dp)
            .fillMaxHeight()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .then(modifier)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){
                Text(text = "Split By", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Row(
                    Modifier
                        .height(50.dp)
                        .padding(top = 12.dp)
                ){
                    Button(onClick = {
                        tipViewModel.updateSplitBy(isIncrement = false)
                        tipViewModel.calculatePerAmount() }, contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(35.dp)
                            .width(50.dp)
                            .defaultMinSize(0.dp)
                    ){
                        Text(text = "-")
                    }
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp, end = 8.dp),
                        contentAlignment = Alignment.Center) {
                        Text(text = tipViewModel.splitBy.toString(), textAlign = TextAlign.Center)
                    }
                    Button(onClick = {
                        tipViewModel.updateSplitBy(isIncrement = true)
                        tipViewModel.calculatePerAmount()
                    }, contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(35.dp)
                            .width(50.dp)
                            .defaultMinSize(0.dp)){
                        Text(text = "+")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ){
                Text(text = "Per Person", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Box (Modifier.padding(top = 10.dp)){
                    Text(text = convertedPerPerson, fontSize = 30.sp)
                }
            }
        }
    }
}
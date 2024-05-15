package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun PerPersonAmountCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
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
            Text("Per Person", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "$4.00")
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
            Text("Split By", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "1")
        }

        Box(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, start = 8.dp, end = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth()){
                Button(modifier = Modifier.weight(1f).padding(end = 3.dp).size(size = 30.dp), onClick = {}){Text(text = "-")}
                Button(modifier = Modifier.weight(1f).padding(start = 3.dp).size(size = 30.dp), onClick = {}){Text(text="+")}
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
//@Composable
//fun SplitByOverview(modifier: Modifier, tipViewModel: TipViewModel)
//{
//    val format: NumberFormat = NumberFormat.getCurrencyInstance()
//    format.setMaximumFractionDigits(2)
//    format.setCurrency(Currency.getInstance("USD"))
//
//    val convertedPerPerson = format.format(tipViewModel.perPersonAmount)
//
//    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surfaceVariant,
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            //.height(100.dp)
//            .fillMaxHeight()
//            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
//            .then(modifier)
//    ){
//        Box (modifier = Modifier.padding(16.dp).fillMaxSize()) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    //.fillMaxHeight()
//
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(1f)
//                ) {
//                    Text(text = "Split By", fontSize = 15.sp, fontWeight = FontWeight.Bold)
//                    Row(
//                        Modifier
//                            .height(50.dp)
//                            .padding(top = 12.dp)
//                    ) {
//                        Button(
//                            onClick = {
//                                tipViewModel.updateSplitBy(isIncrement = false)
//                                tipViewModel.calculatePerAmount()
//                            }, contentPadding = PaddingValues(0.dp),
//                            modifier = Modifier
//                                .height(35.dp)
//                                .width(50.dp)
//                                .defaultMinSize(0.dp)
//                        ) {
//                            Text(text = "-")
//                        }
//                        Box(
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .padding(start = 8.dp, end = 8.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = tipViewModel.splitBy.toString(),
//                                textAlign = TextAlign.Center
//                            )
//                        }
//                        Button(
//                            onClick = {
//                                tipViewModel.updateSplitBy(isIncrement = true)
//                                tipViewModel.calculatePerAmount()
//                            }, contentPadding = PaddingValues(0.dp),
//                            modifier = Modifier
//                                .height(35.dp)
//                                .width(50.dp)
//                                .defaultMinSize(0.dp)
//                        ) {
//                            Text(text = "+")
//                        }
//                    }
//                }
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(1f),
//                    horizontalAlignment = Alignment.End
//                ) {
//                    Text(text = "Per Person", fontSize = 15.sp, fontWeight = FontWeight.Bold)
//                    Box(Modifier.padding(top = 10.dp)) {
//                        Text(text = convertedPerPerson, fontSize = 30.sp)
//                    }
//                }
//            }
//            Column(modifier = Modifier.align(Alignment.BottomCenter)){
//                HorizontalDivider()
//                Box(contentAlignment = Alignment.BottomEnd) {
//                    TextButton(onClick = { /*TODO*/ }) {
//                        Text("Copy")
//                    }
//                }
//            }
//
//        }
//
//    }
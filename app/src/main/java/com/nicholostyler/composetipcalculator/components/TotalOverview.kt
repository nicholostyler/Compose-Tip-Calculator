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

//@Composable
//fun TotalOverview(modifier: Modifier, tipViewModel: TipViewModel)
//{
//    // Convert values to currency
//    val format: NumberFormat = NumberFormat.getCurrencyInstance()
//    format.setMaximumFractionDigits(2)
//    format.setCurrency(Currency.getInstance("USD"))
//
//    val convertedTotal = format.format(tipViewModel.billTotal)
//    val convertedTotalTip = format.format(tipViewModel.totalWithTip)
//    val convertedTipTotal = format.format(tipViewModel.taxTotal)
//
//    val tipCalcState = remember {
//        TipViewModel()
//    }
//
//    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surfaceVariant,
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            //.height(200.dp)
//            .fillMaxHeight()
//            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
//            .then(modifier)
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize().padding(16.dp)
//        ){
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Column(modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)) {
//                    Text("Bill Total", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                    Text(text = convertedTotal)
//                }
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                        .weight(1f), horizontalAlignment = Alignment.End
//                ){
//                    Column(modifier = Modifier
//                    ){
//                        var selectedTipPercentage = tipViewModel.selectedTipPercentage.toString()
//                        Text("Total + Tip", fontWeight = FontWeight.Bold)
//                        Text(text = convertedTotalTip)
//                        Text(text ="Tip ($selectedTipPercentage%)", fontWeight = FontWeight.Bold)
//                        Text(text = convertedTipTotal)
//                    }
//                }
//            }
//            Box(modifier = Modifier.fillMaxSize()) {
//                TextButton(onClick = { /*TODO*/ }) {
//                    Text("Copy")
//                }
//            }
//        }
//
//
//    }
//}

@Composable
fun TotalOverview(modifier: Modifier, tipViewModel: TipViewModel)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .then(modifier)
    ) {
        TotalCard(modifier = Modifier.weight(1f), tipViewModel = tipViewModel)
        TipCard(modifier = Modifier.weight(1f), tipViewModel = tipViewModel)

    }
}

@Composable
fun TipCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
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
            .fillMaxHeight()
            .padding(16.dp)
            .weight(1f)
        ) {
            Text("Tip Total (10%)", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "$4.00")
            Text("Tip Per Person", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "$4.00")
        }
    }
}

@Composable
fun TotalCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
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
            Text("Bill Total", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "$4.00")
            Text("Total With Tip", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            Text(text = "$4.00")
        }
    }
}

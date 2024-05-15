package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
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
fun TopCards(modifier: Modifier, tipViewModel: TipViewModel) {
    Row(modifier = Modifier
        .horizontalScroll(rememberScrollState())
        .then(modifier)
        .width(1000.dp)
        .fillMaxHeight())
    {
            SplitByAddCard(modifier = modifier.width(200.dp), tipViewModel = tipViewModel)
            TotalTopCard(modifier = modifier, tipViewModel = tipViewModel)

            PerPersonCard(modifier = modifier.width(200.dp), tipViewModel = tipViewModel)
            TotalTipCard(modifier = modifier, tipViewModel = tipViewModel)
            TipTopCard(modifier = modifier, tipViewModel = tipViewModel)
            TipSplitCard(modifier = modifier, tipViewModel = tipViewModel)

    }
    }

@Composable
fun TipTopCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
{
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, end = 8.dp)
            .then(modifier)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

            .weight(1f)) {
            Text("Tip Total (10%)", fontWeight = FontWeight.Bold)
            Text(text = tipViewModel.taxTotal.toString())
        }
    }
}

@Composable
fun TotalTopCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
{
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            //.height(200.dp)
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .then(modifier)

    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

        ) {
            Text("Bill Total", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = tipViewModel.billTotal.toString())
        }
    }
}

@Composable
fun TotalTipCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
{
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            //.height(200.dp)
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .then(modifier)

    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

        ) {
            Text("Total + Tip", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = tipViewModel.totalWithTip.toString())
        }
    }
}

@Composable
fun TipSplitCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
{
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            //.height(200.dp)
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .then(modifier)

    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

        ) {
            Text("Tip Per Person", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "$1.25")
        }
    }
}

@Composable
fun SplitByAddCard(modifier: Modifier, tipViewModel: TipViewModel)
{
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            //.height(200.dp)
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .then(modifier)

    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ){
            Text(text = "Split By", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Row(
                Modifier
                    .height(50.dp)
                    .padding(top = 4.dp)
            ) {
                Button(
                    onClick = {
                        tipViewModel.updateSplitBy(isIncrement = false)
                        tipViewModel.calculatePerAmount()
                    }, contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .height(35.dp)
                        .width(50.dp)
                        .defaultMinSize(0.dp)
                ) {
                    Text(text = "-")
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp, end = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = tipViewModel.splitBy.toString(), textAlign = TextAlign.Center)
                }
                Button(
                    onClick = {
                        tipViewModel.updateSplitBy(isIncrement = true)
                        tipViewModel.calculatePerAmount()
                    }, contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .height(35.dp)
                        .width(50.dp)
                        .defaultMinSize(0.dp)
                ) {
                    Text(text = "+")
                }
            }
        }

    }
}

    @Composable
    fun PerPersonCard(modifier: Modifier = Modifier, tipViewModel: TipViewModel)
    {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                //.height(200.dp)
                .fillMaxSize()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                .then(modifier)

        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

            ) {
                Text("Total Per Person", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = tipViewModel.perPersonAmount.toString())
            }
        }
    }
    

package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nicholostyler.composetipcalculator.TipViewModel

@Composable
fun TipCard(percent: Int, totalValue: String, tipViewModel: TipViewModel)
{
    Card(modifier = Modifier
        .padding(8.dp)
        .height(height = 120.dp)
    )
    {
        Column(
            Modifier.fillMaxSize()
        ){
            Text(text = "Total at $percent%", modifier = Modifier.padding(8.dp))
            Text(text = totalValue, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp), textAlign = TextAlign.Start, fontWeight = FontWeight.Bold)
            TextButton(onClick = {
                tipViewModel.updateSelectedPercentage(percent)
                if (tipViewModel.openCustomDisplay.value)
                {
                    tipViewModel.changeCustomDisplay()
                }
            },
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(8.dp)) {
                Text(text = "Select")
            }
        }

    }
}
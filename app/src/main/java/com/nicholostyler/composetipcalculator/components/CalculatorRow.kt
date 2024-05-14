package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nicholostyler.composetipcalculator.TipViewModel

@Composable
fun CalculatorRow(values: List<ButtonSpec>, modifier: Modifier, tipViewModel: TipViewModel,)
{
    Row(
        modifier = Modifier
            //.padding(4.dp)
            .then(modifier)
    ){
        values.map { buttonSpec ->
            CalculatorButton(buttonSpec, modifier = Modifier.weight(1f), tipViewModel,)
        }
    }
}
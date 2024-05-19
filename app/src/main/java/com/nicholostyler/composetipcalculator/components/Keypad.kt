package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicholostyler.composetipcalculator.TipViewModel

@Composable
fun Keypad(
    modifier: Modifier = Modifier,
    tipViewModel: TipViewModel,
)
{
    Column(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Bottom
    ) {
        rows.map { buttonSpecs ->
            CalculatorRow(buttonSpecs, modifier = Modifier.weight(1f), tipViewModel)
        }
    }
}

val rows = listOf(
    listOf(ButtonSpec("1"), ButtonSpec("2"), ButtonSpec("3")),
    listOf(ButtonSpec("4"), ButtonSpec("5"), ButtonSpec("6")),
    listOf(ButtonSpec("7"), ButtonSpec("8"), ButtonSpec("9")),
    listOf(ButtonSpec("."), ButtonSpec("0"), ButtonSpec("x"))
)

data class ButtonSpec(
    val label: String
)
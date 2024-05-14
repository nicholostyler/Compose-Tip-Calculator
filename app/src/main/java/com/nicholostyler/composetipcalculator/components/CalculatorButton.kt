package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicholostyler.composetipcalculator.TipViewModel

@Composable
fun CalculatorButton(buttonSpec: ButtonSpec, modifier: Modifier, tipViewModel: TipViewModel)
{
    BoxWithConstraints(modifier) {
        val boxWithConstraintsScope = this
        var paddingValue = 0.dp
        if (boxWithConstraintsScope.minHeight > 550.dp)
        {
            paddingValue = 18.dp
        }
        Button(
            onClick = {
                tipViewModel.updateBillTotal(buttonSpec.label)
            },
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.Transparent),
            contentPadding = PaddingValues(paddingValue),
            modifier = modifier.padding(4.dp).then(modifier).fillMaxSize()
        ){
            Text(
                text = buttonSpec.label,
                fontSize = 22.sp
            )
        }
    }

}
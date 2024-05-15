package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicholostyler.composetipcalculator.TipViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipPercentView(
    modifier: Modifier = Modifier,
    tipViewModel: TipViewModel
)
{
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp)
        .then(modifier)) {
        //TODO: Change this based on screen size
        val options = tipViewModel.segmentedButtonsList
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size,),
                    onClick = { percentButtonClick(tipViewModel, options[index]) },
                    selected = index == tipViewModel.selectedSegmentedTip,
                ) {
                    Text(label)

                }
            }
        }
    }
}

fun percentButtonClick(tipViewModel: TipViewModel, value: String)
{
    if (value == "Custom")
    {
        tipViewModel.changeCustomDisplay()
    }
    else
    {
        tipViewModel.updateSelectedTipPercentage(value)
    }
}
package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
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
        val options = listOf("10%","18%", "20%", "Custom")
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    onClick = { percentButtonClick(index, tipViewModel) },
                    selected = index == tipViewModel.selectedSegmentedTip
                ) {
                    Text(label)

                }
            }
        }
    }
}

fun percentButtonClick(index: Int, tipViewModel: TipViewModel)
{
    if (index == 4)
    {
        tipViewModel.changeCustomDisplay()
    }
    else
    {
        tipViewModel.updateSelectedTipPercentage(index)
    }
}
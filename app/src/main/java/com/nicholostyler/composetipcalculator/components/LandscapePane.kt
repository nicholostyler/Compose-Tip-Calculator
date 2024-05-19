package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicholostyler.composetipcalculator.TipViewModel

@Composable
fun LandscapePane(modifier: Modifier, tipCalcState: TipViewModel) {
    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .safeDrawingPadding()) {
        val boxWithConstraintsScope = this

        tipCalcState.changeSegmentedButtonCount(boxWithConstraintsScope.minWidth)

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .weight(.2f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                )
                {
                    SideCards(modifier = Modifier.weight(1f), tipViewModel = tipCalcState)
                    Column(
                        modifier = Modifier.weight(1f)
                    )
                    {

                        TipPercentView(modifier = Modifier.weight(.6f), tipCalcState)
                        Keypad(modifier = Modifier.weight(2f), tipCalcState,)
                    }
                }
            }
        }
    }
}
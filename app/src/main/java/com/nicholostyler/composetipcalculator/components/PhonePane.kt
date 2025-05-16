package com.nicholostyler.composetipcalculator.components

import android.app.Activity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicholostyler.composetipcalculator.TipViewModel

@Composable
fun PhonePane(modifier: Modifier, activity: Activity, tipCalcState: TipViewModel)
{
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .then(modifier)
    ) {
        val boxWithConstraintsScope = this

        tipCalcState.changeSegmentedButtonCount(boxWithConstraintsScope.minWidth)
        Column(modifier = Modifier.fillMaxSize())
        {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
                    .fillMaxHeight(),
            ) {
                CardGrid(
                    modifier = Modifier
                        //.height(boxWithConstraintsScope.maxHeight /2 )
                        .weight(1f), tipCalcState = tipCalcState
                )
                Column(modifier = Modifier.weight(1f))
                {
                    TipPercentView(modifier = Modifier, tipViewModel = tipCalcState)
                    Keypad(
                        //modifier = Modifier.height(200.dp),

                        tipViewModel = tipCalcState
                    )
                }
            }
        }

        // min height is too small - start scrolling
        /*if (boxWithConstraintsScope.minHeight < 600.dp || boxWithConstraintsScope.minWidth < 350.dp)
        {
            Column(modifier = Modifier.fillMaxSize())
            {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    CardGrid(
                        modifier = Modifier
                            //.height(boxWithConstraintsScope.maxHeight /2 )
                            .height(IntrinsicSize.Max), tipCalcState = tipCalcState
                    )
                    Column(Modifier.height(IntrinsicSize.Max))
                    {
                        TipPercentView(modifier = Modifier, tipViewModel = tipCalcState)
                        Keypad(
                            modifier = Modifier.height(200.dp),
                            tipViewModel = tipCalcState
                        )
                    }
                }
            }
        }*/
        // min height is good - don't scroll
        /*else
        {
            Column(modifier = Modifier.fillMaxSize())
            {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    CardGrid(
                        modifier = Modifier
                            .height(boxWithConstraintsScope.maxHeight / 2)
                        //.defaultMinSize(minHeight = 350.dp)
                        , tipCalcState = tipCalcState
                    )

                    Column(Modifier.height(boxWithConstraintsScope.maxHeight / 2))
                    {
                        TipPercentView(modifier = Modifier, tipViewModel = tipCalcState)
                        Keypad(modifier = Modifier, tipViewModel = tipCalcState)
                    }
                }
            }*/
        }
    }

package com.nicholostyler.composetipcalculator.components

import android.app.Activity
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nicholostyler.composetipcalculator.R
import com.nicholostyler.composetipcalculator.TipViewModel

import com.nicholostyler.composetipcalculator.components.TotalOverview
import com.nicholostyler.composetipcalculator.components.SplitByOverview
import com.nicholostyler.composetipcalculator.components.Keypad
import com.nicholostyler.composetipcalculator.components.PercentCardsList

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainCalculator(activity: Activity, tipCalcState: TipViewModel)
{
    val windowSizeClass = calculateWindowSizeClass(activity = activity)
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    // run calculate based on default values
    tipCalcState.calculatePerAmount()

    // If is wide display (foldable/tablet)
    if (windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium && windowSizeClass.heightSizeClass >= WindowHeightSizeClass.Medium)
    {
        tipCalcState.changeSegmentedButtonCount(buttonCount = 5)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        )
        {
            BoxWithConstraints(modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
                val boxWithConstraintsScope = this
                Column(modifier = Modifier.fillMaxSize() )
                {
                    if (boxWithConstraintsScope.minHeight < 550.dp)
                    {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .weight(1f)
                                .fillMaxHeight(),
                        ){
                            CardGrid(modifier = Modifier.height(IntrinsicSize.Min), tipCalcState = tipCalcState)
                            Keypad(modifier = Modifier.weight(1f), tipViewModel = tipCalcState)
                        }
                    }
                    else
                    {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                        ){
                            CardGrid(modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .weight(1f), tipCalcState = tipCalcState)
                            Keypad(modifier = Modifier.weight(1f), tipViewModel = tipCalcState)
                        }
                    }
                }
            }

            PercentCardsList(modifier = Modifier.weight(1f), tipViewModel = tipCalcState)

        }
    }
    // Is a phone in landscape
    // Is a phone/foldable in split view
    else if (windowSizeClass.heightSizeClass <= WindowHeightSizeClass.Compact)
    {
        // Set Segmented Button to 3
        tipCalcState.changeSegmentedButtonCount(buttonCount = 3)
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()) {
            val boxWithConstraintsScope = this

            if (boxWithConstraintsScope.minHeight < 371.dp || boxWithConstraintsScope.minWidth < 550.dp)
            {
                tipCalcState.changeSegmentedButtonCount(buttonCount = 2)
            }

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
    // Is a regular phone view
    else
    {
        tipCalcState.changeSegmentedButtonCount(buttonCount = 4)
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()) {
            val boxWithConstraintsScope = this

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (boxWithConstraintsScope.minWidth < 290.dp || boxWithConstraintsScope.minHeight < 550.dp)
                {
                    tipCalcState.changeSegmentedButtonCount(buttonCount = 2)
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom)
                    {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .weight(2f, fill = true)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ){
                            CardGrid(modifier = Modifier.height(IntrinsicSize.Max), tipCalcState = tipCalcState)
                            TipPercentView(modifier = Modifier.align(Alignment.End), tipViewModel = tipCalcState)
                        }
                        Keypad(modifier = Modifier.weight(2f), tipViewModel = tipCalcState)

                    }
                }
                else
                {
                    Column(modifier = Modifier.fillMaxSize(), )
                    {
                        Column(
                            modifier = Modifier
                                //.verticalScroll(rememberScrollState())
                                .weight(1f)
                                .fillMaxHeight(),
                        ){
                            CardGrid(modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .weight(1f), tipCalcState = tipCalcState)
                            TipPercentView(modifier = Modifier, tipViewModel = tipCalcState)
                            Keypad(modifier = Modifier.weight(1f), tipViewModel = tipCalcState)
                        }
                    }
                }


                }
            }
        }

    // BUG: Lags when closing with gesture nav
    // potential fix: https://medium.com/@giuliopime/modalbottomsheet-and-the-system-navigation-bar-jetpack-compose-6e9bf58e8317
    if (tipCalcState.openCustomDisplay.value)
    {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { tipCalcState.changeCustomDisplay()}
        ) {
            PercentCardsList(tipViewModel = tipCalcState)
        }
    }

}


@Composable
fun CardGrid(modifier: Modifier, tipCalcState: TipViewModel)
{
        Column(
            Modifier
                .fillMaxSize()
                .then(modifier)
        ) {
            SmallAppBar(tipViewModel = tipCalcState)
            TotalOverview(modifier = modifier.weight(1f), tipViewModel = tipCalcState)
            SplitByOverview(
                modifier = modifier
                    .weight(1f), tipViewModel = tipCalcState
            )
        }
}
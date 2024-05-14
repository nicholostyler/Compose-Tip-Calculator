package com.nicholostyler.composetipcalculator.components

import android.app.Activity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Row(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        )
        {
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            ) {
                TotalOverview(modifier = Modifier.weight(.25f), tipCalcState)
                SplitByOverview(modifier = Modifier.weight(.25f), tipCalcState)
                //TipPercentView(modifier = Modifier.weight(.5f), tipCalcState)
                //HorizontalDivider(modifier = Modifier.weight(.10f).align(Alignment.CenterHorizontally))
                Keypad(modifier = Modifier.weight(.5f), tipCalcState,)
            }
            PercentCardsList(modifier = Modifier.weight(1f), tipViewModel = tipCalcState)
        }
    }
    // Is a phone in landscape
    // Is a phone/foldable in split view
    // BUG: Can't hold 5 in segmented button view
    else if (windowSizeClass.heightSizeClass <= WindowHeightSizeClass.Compact)
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        )
        {
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(.6f)
            ) {
                TotalOverview(modifier = Modifier.weight(1f), tipCalcState)
                SplitByOverview(modifier = Modifier.weight(.8f), tipCalcState)
                TipPercentView(modifier = Modifier.weight(.5f), tipCalcState)
                //HorizontalDivider(modifier = Modifier.padding(8.dp))
                //Keypad(modifier = Modifier.weight(2f), tipCalcState)
            }
            Keypad(modifier = Modifier.weight(.4f), tipCalcState, )
        }
    }
    else
    {
        BoxWithConstraints(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
            val boxWithConstraintsScope = this

            Column(modifier = Modifier
                .fillMaxSize()
            ) {
                if (boxWithConstraintsScope.minHeight < 550.dp)
                {
                    TopCards(modifier = Modifier.weight(1f), tipViewModel = tipCalcState)
                    TipPercentView(modifier = Modifier.weight(.5f), tipCalcState)
                    Keypad(modifier = Modifier.weight(2f), tipCalcState, )
                }
                else
                {
                    TotalOverview(modifier = Modifier.weight(1f), tipCalcState)
                    SplitByOverview(modifier = Modifier.weight(.8f), tipCalcState)
                    //TopCards(modifier = Modifier.weight(1f), tipViewModel = tipCalcState)
                    TipPercentView(modifier = Modifier.weight(.5f), tipCalcState)
                    Keypad(modifier = Modifier.weight(2f), tipCalcState, )
                }
                }

        }

    }

    // Display a modal bottom sheet when user clicks CUSTOM tip button
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
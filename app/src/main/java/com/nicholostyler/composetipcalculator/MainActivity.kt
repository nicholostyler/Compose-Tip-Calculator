package com.nicholostyler.composetipcalculator

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.layout.WindowInfoTracker
import com.nicholostyler.composetipcalculator.ui.theme.ComposeTipCalculatorTheme
import java.text.NumberFormat
import java.util.Currency

@ExperimentalMaterial3WindowSizeClassApi
class MainActivity : ComponentActivity() {
    private var windowInfoTracker: WindowInfoTracker =
        WindowInfoTracker.getOrCreate(this@MainActivity)

    val tipCalcState: TipViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge to edge
        enableEdgeToEdge(
        )

        super.onCreate(savedInstanceState)

        setContent {
            ComposeTipCalculatorTheme {
                // Check if device is wide display
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = colorScheme.background,
                ) {
                    MainCalculator(this, tipCalcState)
                }
            }
        }
    }
}

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
                Keypad(modifier = Modifier.weight(.5f), tipCalcState)
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
                .weight(1f)
            ) {
                TotalOverview(modifier = Modifier.weight(1f), tipCalcState)
                SplitByOverview(modifier = Modifier.weight(.8f), tipCalcState)
                TipPercentView(modifier = Modifier.weight(.5f), tipCalcState)
                //HorizontalDivider(modifier = Modifier.padding(8.dp))
                //Keypad(modifier = Modifier.weight(2f), tipCalcState)
            }
            Keypad(modifier = Modifier.weight(1f), tipCalcState)
        }
    }
    else
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
        ) {
            TotalOverview(modifier = Modifier.weight(1f), tipCalcState)
            SplitByOverview(modifier = Modifier.weight(.8f), tipCalcState)
            TipPercentView(modifier = Modifier.weight(.5f), tipCalcState)
            Keypad(modifier = Modifier.weight(2f), tipCalcState)
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

@Composable
fun PercentCardsList(
    modifier: Modifier = Modifier.fillMaxSize(), tipViewModel: TipViewModel
)
{
    // Create list of percent values
    var percentArray = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)
    // Create list of cards
    LazyColumn(modifier = modifier.fillMaxWidth()){
        items(percentArray) { percent ->
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.setMaximumFractionDigits(2)
            format.setCurrency(Currency.getInstance("USD"))

            val total = format.format(tipViewModel.calculatePerAmount(percent))
            TipCard(percent, total, tipViewModel)
        }
    }
}

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

@Composable
fun TotalView(
    modifier: Modifier = Modifier, tipViewModel: TipViewModel
)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
            .then(modifier)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                //.padding(start = 10.dp, top = 10.dp, bottom = 0.dp)
                .then(modifier)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(end = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 10.dp)
                ) {
                    Text(
                        text = "Initial Balance"
                    )
                    Text(
                        text = tipViewModel.billTotal.toString(),
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                }

            }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                horizontalAlignment = Alignment.End
        ){
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                ) {
                    Text(
                        text = "Total Tax"
                    )
                    Text(
                        text = tipViewModel.taxTotal.toString(),
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                }

            }
        }
    }

}

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
        val options = listOf("10%", "15%", "18%", "20%", "Custom")
        val openAlertDialog = remember { mutableStateOf(false)}
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    onClick = {
                        //TODO:
                        //seperate into own method
                        // add custom tip dialog
                        if (index == 4)
                        {
                            tipViewModel.changeCustomDisplay()
                        }
                        else
                        {
                            tipViewModel.updateSelectedTipPercentage(index)
                        }
                              },
                    selected = index == tipViewModel.selectedSegmentedTip
                ) {
                    Text(label)

                }
            }
        }
    }
}

@Composable
fun SplitView(
    modifier: Modifier = Modifier,
    tipViewModel: TipViewModel
)
{
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(text = "Split By")
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    Button(
                        onClick =
                        {
                            tipViewModel.updateSplitBy(isIncrement = false)
                            tipViewModel.calculatePerAmount()
                        }
                    ){
                        Text(text = "-")
                    }
                    Text(
                        text = tipViewModel.splitBy.toString(),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 5.dp, end = 5.dp)
                            .wrapContentHeight(Alignment.CenterVertically),

                        textAlign = TextAlign.Center,
                    )
                    Button(
                        onClick =
                        {
                            tipViewModel.updateSplitBy(isIncrement = true)
                            tipViewModel.calculatePerAmount()
                        },

                    ){
                        Text(text = "+")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(end = 10.dp)
                    .align(Alignment.Top),
                horizontalAlignment = Alignment.End

            ) {
                Text(text = "Per Person")
                Text(
                    text = tipViewModel.perPersonAmount.toString(),
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun Keypad(
    modifier: Modifier = Modifier,
    tipViewModel: TipViewModel
)
{
   Column(
       modifier = Modifier
           .padding(8.dp)
           .fillMaxSize()
           .then(modifier),
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

@Composable
fun CalculatorRow(values: List<ButtonSpec>, modifier: Modifier, tipViewModel: TipViewModel)
{
    Row(
        modifier = Modifier
            //.padding(4.dp)
            .then(modifier)
    ){
        values.map { buttonSpec ->
            CalculatorButton(buttonSpec, modifier = Modifier.weight(1f), tipViewModel)
        }
    }
}

@Composable
fun CalculatorButton(buttonSpec: ButtonSpec, modifier: Modifier, tipViewModel: TipViewModel)
{
    Button(
        onClick = {
            tipViewModel.updateBillTotal(buttonSpec.label)
        },
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.Transparent),
        contentPadding = PaddingValues(18.dp),
        modifier = modifier.padding(4.dp)
    ){
        Text(
            text = buttonSpec.label,
            fontSize = 22.sp
        )
    }
}

@Composable
fun SplitByOverview(modifier: Modifier, tipViewModel: TipViewModel)
{
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.setMaximumFractionDigits(2)
    format.setCurrency(Currency.getInstance("USD"))

    val convertedPerPerson = format.format(tipViewModel.perPersonAmount)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .then(modifier)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){
                Text(text = "Split By", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Row(
                    Modifier
                        .height(50.dp)
                        .padding(top = 12.dp)
                ){
                    Button(onClick = {
                        tipViewModel.updateSplitBy(isIncrement = false)
                        tipViewModel.calculatePerAmount() }, contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(35.dp)
                            .width(50.dp)
                            .defaultMinSize(0.dp)
                    ){
                        Text(text = "-")
                    }
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp, end = 8.dp),
                        contentAlignment = Alignment.Center) {
                        Text(text = tipViewModel.splitBy.toString(), textAlign = TextAlign.Center)
                    }
                    Button(onClick = {
                        tipViewModel.updateSplitBy(isIncrement = true)
                        tipViewModel.calculatePerAmount()
                    }, contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(35.dp)
                            .width(50.dp)
                            .defaultMinSize(0.dp)){
                        Text(text = "+")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                    horizontalAlignment = Alignment.End
            ){
                Text(text = "Per Person", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Box (Modifier.padding(top = 10.dp)){
                    Text(text = convertedPerPerson, fontSize = 30.sp)
                }
            }
        }
    }
}

@Composable
fun TotalOverview(modifier: Modifier, tipViewModel: TipViewModel)
{
    // Convert values to currency
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.setMaximumFractionDigits(2)
    format.setCurrency(Currency.getInstance("USD"))

    val convertedTotal = format.format(tipViewModel.billTotal)
    val convertedTotalTip = format.format(tipViewModel.totalWithTip)
    val convertedTipTotal = format.format(tipViewModel.taxTotal)

    val tipCalcState = remember {
        TipViewModel()
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Text("Bill Total", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = convertedTotal)
            }
            VerticalDivider()
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 8.dp),
                horizontalAlignment = Alignment.End
            ){
                var selectedTipPercentage = tipViewModel.selectedTipPercentage.toString()
                Text("Total + Tip", fontWeight = FontWeight.Bold)
                Text(text = convertedTotalTip)
                Text(text ="Tip ($selectedTipPercentage%)", fontWeight = FontWeight.Bold)
                Text(text = convertedTipTotal)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun isWideDisplay(activity: Activity = LocalContext.current as Activity): Boolean
{
    val windowSizeClass = calculateWindowSizeClass(activity = activity)
    val isWideDisplay: Boolean by remember {
        derivedStateOf {
            windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium
        }
    }

    return isWideDisplay
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun isPhoneLandscape(activity: Activity = LocalContext.current as Activity): Boolean
{
    val windowSizeClass = calculateWindowSizeClass(activity = activity)
    val isPhoneLandscape: Boolean by remember {
        derivedStateOf {
            windowSizeClass.heightSizeClass >= WindowHeightSizeClass.Compact
        }
    }
    return isPhoneLandscape
}

//@Preview(showBackground = true, heightDp = 360, widthDp = 800)
@Preview(showBackground = true, device = Devices.PIXEL_FOLD)
@Composable
fun GreetingPreview() {
    ComposeTipCalculatorTheme {
        val tipCalcState = remember {
            TipViewModel()
        }
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
                TotalOverview(modifier = Modifier.weight(.3f), tipCalcState)
                SplitByOverview(modifier = Modifier.weight(.25f), tipCalcState)
                //TipPercentView(modifier = Modifier.weight(.5f), tipCalcState)
                //HorizontalDivider(modifier = Modifier.weight(.10f).align(Alignment.CenterHorizontally))
                Keypad(modifier = Modifier.weight(.4f), tipCalcState)
            }
            PercentCardsList(modifier = Modifier.weight(1f), tipViewModel = tipCalcState)
        }

    }
}
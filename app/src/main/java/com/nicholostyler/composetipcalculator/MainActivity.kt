package com.nicholostyler.composetipcalculator

import android.app.Activity
import androidx.window.layout.WindowInfoTracker
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nicholostyler.composetipcalculator.ui.theme.ComposeTipCalculatorTheme
import java.time.format.TextStyle

@ExperimentalMaterial3WindowSizeClassApi
class MainActivity : ComponentActivity() {
    private var windowInfoTracker: WindowInfoTracker =
        WindowInfoTracker.getOrCreate(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge to edge
        enableEdgeToEdge(
        )
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTipCalculatorTheme {
                // Check if device is wide display
                val isWideDisplay = isWideDisplay(this)
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = colorScheme.background,
                ) {
                    MainCalculator(isWideDisplay)
                }
            }
        }
    }
}

@Composable
fun MainCalculator(isWideDisplay: Boolean)
{
    // main ViewModel
    val tipCalcState = remember {
        TipViewModel()
    }

    // run calculate based on default values
    tipCalcState.calculatePerAmount()

    if (isWideDisplay)
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
                //TipPercentView(modifier = Modifier.weight(.5f), tipCalcState)
                HorizontalDivider(modifier = Modifier.padding(8.dp))
                Keypad(modifier = Modifier.weight(2f), tipCalcState)
            }
            PercentCardsList(tipViewModel = tipCalcState)
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

}

@Composable
fun PercentCardsList(
    modifier: Modifier = Modifier, tipViewModel: TipViewModel
)
{
    // Create list of percent values
    var percentArray = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)
    // Create list of cards
    LazyColumn{
        items(percentArray) { percent ->
            TipCard(percent, tipViewModel)
        }
    }
}

@Composable
fun TipCard(percent: Int, tipViewModel: TipViewModel)
{
    Card(modifier = Modifier
        .padding(8.dp)
        .size(width = 240.dp, height = 120.dp))
    {
        Column(
            Modifier.fillMaxSize()
        ){
            Text(text = "Total at $percent%", modifier = Modifier.padding(8.dp))
            Text(text = "$9.25", modifier = Modifier
                .fillMaxWidth().padding(start = 8.dp), textAlign = TextAlign.Start, fontWeight = FontWeight.Bold)
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.align(alignment = Alignment.End).padding(8.dp)) {
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
                        tipViewModel.updateSelectedTipPercentage(index)
                              },
                    selected = index == tipViewModel.selectedTipPercentage
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
                Text(text = "Per Person", fontSize = 15.sp)
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
                Text(text = "Per Person", fontSize = 15.sp)
                Box (Modifier.padding(top = 10.dp)){
                    Text(text = tipViewModel.perPersonAmount.toString(), fontSize = 30.sp)
                }
            }
        }
    }
}

@Composable
fun TotalOverview(modifier: Modifier, tipViewModel: TipViewModel)
{
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
                Text(text = tipViewModel.billTotal.toString())
            }
            VerticalDivider()
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 8.dp),
                horizontalAlignment = Alignment.End
            ){
                Text("Total + Tip")
                Text(text = tipViewModel.totalWithTip.toString())
                Text(text ="Tip")
                Text(text = tipViewModel.taxTotal.toString())
            }
        }

    }
}

@Composable
fun isTablet(): Boolean {
    return LocalConfiguration.current.screenWidthDp >= 600
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

@Preview(showBackground = true, device = Devices.PIXEL_FOLD)
@Composable
fun GreetingPreview() {
    ComposeTipCalculatorTheme {
        MainCalculator(isWideDisplay = true)
        val tipCalcState = remember {
            TipViewModel()
        }
        //TipCard(modifier = Modifier, tipCalcState)
        //PercentCardsList(tipViewModel = tipCalcState)

    }
}
package com.nicholostyler.composetipcalculator

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.nicholostyler.composetipcalculator.ui.theme.ComposeTipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge to edge
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb(),
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            )
        )
        super.onCreate(savedInstanceState)

        setContent {

            ComposeTipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().safeDrawingPadding(),
                    color = colorScheme.background

                ) {

                    MainCalculator()
                }
            }
        }
    }
}

@Composable
fun MainCalculator()
{
    // main ViewModel
    val tipCalcState = remember {
        TipViewModel()
    }

    // run calculate based on default values
    tipCalcState.calculatePerAmount()

    Column(modifier = Modifier
        .fillMaxSize()
        //.safeDrawingPadding()
        //.safeContentPadding()
        ) {
        TotalView(modifier = Modifier.weight(1f), tipCalcState)
        TipPercentView(modifier = Modifier.weight(1f), tipCalcState)
        SplitView(modifier = Modifier.weight(1f), tipCalcState)
        Keypad(modifier = Modifier.weight(3f), tipCalcState)
    }
}

@Composable
fun TotalView(
    modifier: Modifier = Modifier, tipViewModel: TipViewModel
)
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, bottom = 0.dp)
            .then(modifier)
    ) {
        Text(
            text = "Initial Balance"
        )
        Text(
            text = tipViewModel.billTotal.toString(),
            fontSize = 40.sp,
            modifier = Modifier
                .padding(top = 10.dp)
        )
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
        Text(text = "Tip Percentage")
        val options = listOf("10%", "15%", "18%", "20%", "Custom")
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .padding(top = 20.dp)

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
            .padding(4.dp)
            .then(modifier)
    ){
        values.map { buttonSpec ->
            CalculatorButton(buttonSpec, modifier = Modifier.weight(1f), tipViewModel)
        }
    }
}


/*@Composable
fun KeypadRow(
    numbers: Array<String> = arrayOf("1", "2", "3"),
    modifier: Modifier = Modifier
)
{
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        CalculatorButton(numbers[0], modifier = Modifier
            .weight(1f)
            .aspectRatio(1f))
        CalculatorButton(numbers[1], modifier = Modifier
            .weight(1f)
            .aspectRatio(1f))
        CalculatorButton(numbers[2], modifier = Modifier
            .weight(1f)
            .aspectRatio(1f))
    }
}*/

@Composable
fun CalculatorButton(buttonSpec: ButtonSpec, modifier: Modifier, tipViewModel: TipViewModel)
{
    OutlinedButton(
        onClick = {
            tipViewModel.updateBillTotal(buttonSpec.label)
                  },
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.Transparent),
        contentPadding = PaddingValues(18.dp),
        modifier = modifier
    ) {
        Text(
            text = buttonSpec.label,
            fontSize = 22.sp
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTipCalculatorTheme {
        MainCalculator()
    }
}
package com.nicholostyler.composetipcalculator

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.layout.WindowInfoTracker
import com.nicholostyler.composetipcalculator.components.CardGrid
import com.nicholostyler.composetipcalculator.components.Keypad
import com.nicholostyler.composetipcalculator.components.MainCalculator
import com.nicholostyler.composetipcalculator.components.PerPersonCard
import com.nicholostyler.composetipcalculator.components.PercentCardsList
import com.nicholostyler.composetipcalculator.components.SideCards
import com.nicholostyler.composetipcalculator.components.SmallAppBar
import com.nicholostyler.composetipcalculator.components.SplitByAddCard
import com.nicholostyler.composetipcalculator.components.SplitByOverview
import com.nicholostyler.composetipcalculator.components.TipPercentView
import com.nicholostyler.composetipcalculator.components.TipSplitCard
import com.nicholostyler.composetipcalculator.components.TipTopCard
import com.nicholostyler.composetipcalculator.components.TopCards
import com.nicholostyler.composetipcalculator.components.TotalOverview
import com.nicholostyler.composetipcalculator.components.TotalTipCard
import com.nicholostyler.composetipcalculator.components.TotalTopCard
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






@Preview(fontScale = 0.85f, name = "Scale 85%", group = "Font Scaling")
@Preview(fontScale = 1f, name = "Scale 100%", group = "Font Scaling")
@Preview(fontScale = 1.15f, name = "Scale 115%", group = "Font Scaling")
@Preview(fontScale = 1.3f, name = "Scale 130%", group = "Font Scaling")
@Preview(fontScale = 1.5f, name = "Scale 150%", group = "Font Scaling")
@Preview(fontScale = 1.8f, name = "Scale 180%", group = "Font Scaling")
@Preview(fontScale = 2f, name = "Scale 200%", group = "Font Scaling")
//@Preview(showBackground = true, device = Devices.PIXEL_FOLD)
//@Preview(showBackground = true, device = Devices.PIXEL_TABLET)
@Composable
fun GreetingPreview()
{
    ComposeTipCalculatorTheme {
        val tipCalcState = remember {
            TipViewModel()
        }

        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()) {
            val boxWithConstraintsScope = this
            tipCalcState.changeSegmentedButtonCount(boxWithConstraintsScope.minWidth)

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween)
            {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f)
                        .fillMaxSize(),
                ){
                    CardGrid(modifier = Modifier
                        .height(boxWithConstraintsScope.maxHeight /2 )
                        //.defaultMinSize(minHeight = 350.dp)
                        , tipCalcState = tipCalcState)

                    Column(Modifier.height(boxWithConstraintsScope.maxHeight /2))
                    {
                        TipPercentView(modifier = Modifier, tipViewModel = tipCalcState)
                        Keypad(modifier = Modifier, tipViewModel = tipCalcState)
                    }
                }
            }
            }

    }
    }




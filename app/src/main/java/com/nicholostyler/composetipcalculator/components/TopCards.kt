package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicholostyler.composetipcalculator.TipViewModel

@Composable
fun TopCards(modifier: Modifier, tipViewModel: TipViewModel) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState()).then(modifier).width(500.dp).fillMaxHeight())
    {
            TotalOverview(modifier = modifier.width(200.dp), tipViewModel = tipViewModel)
            SplitByOverview(modifier = modifier, tipViewModel = tipViewModel)
    }
    }
    

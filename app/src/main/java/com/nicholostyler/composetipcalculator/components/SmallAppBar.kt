package com.nicholostyler.composetipcalculator.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.nicholostyler.composetipcalculator.R
import com.nicholostyler.composetipcalculator.TipViewModel

@Composable
fun SmallAppBar(tipViewModel: TipViewModel)
{
    val clipboardContext = LocalClipboardManager.current

    Row(modifier = Modifier)
    {
        Spacer(modifier = Modifier.weight(1f))
//        IconButton(onClick = { /*TODO*/ }) {
//            Icon(
//                imageVector = Icons.Filled.Settings,
//                contentDescription = "Settings",
//                tint = Color.Black
//            )
//        }
        IconButton(onClick = { copyButtonClicked(clipboardContext,tipViewModel.toClipboard())},) {
            Icon(
                painter = painterResource(id = R.drawable.copy_light),
                contentDescription = "Copy Tip",
                Modifier.size(25.dp)
            )
        }
    }
}

fun copyButtonClicked(context: ClipboardManager, text: String)
{
    context.setText(AnnotatedString(text))

}
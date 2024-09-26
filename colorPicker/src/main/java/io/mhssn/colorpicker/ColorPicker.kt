package io.mhssn.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.mhssn.colorpicker.ext.toHex
import io.mhssn.colorpicker.ext.transparentBackground
import io.mhssn.colorpicker.pickers.ClassicColorPicker

@ExperimentalComposeUiApi
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    onPickedColor: (Color) -> Unit
) {
    Box(modifier = modifier) {
        ClassicColorPicker(
            showAlphaBar = true,  // Default to showing the alpha bar as in the Classic type
            onPickedColor = onPickedColor
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun ColorPickerDialog(
    show: Boolean,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    onPickedColor: (Color) -> Unit,
    viewModel: ToolViewModel 

) {
    var showDialog by remember(show) {
        mutableStateOf(show)
    }
    var color by remember {
        mutableStateOf(Color.White)
    }
    if (showDialog) {
        Dialog(onDismissRequest = {
            onDismissRequest()
            showDialog = false
        }, properties = properties) {
            Box(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White)
            ) {
                Box(modifier = Modifier.padding(32.dp)) {
                    Column {
                        ColorPicker(onPickedColor = {
                            color = it
                        })
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp, 30.dp)
                                    .clip(RoundedCornerShape(50))
                                    .border(0.3.dp, Color.LightGray, RoundedCornerShape(50))
                                    .transparentBackground(verticalBoxesAmount = 4)
                                    .background(color)
                            )
                        
                        }
                        Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp)
) {
    
    OutlinedButton(
        modifier = Modifier.weight(1f),
        onClick = {
            onPickedColor(color)
            viewModel.saveBrushColor(color)
            showDialog = false
            viewModel.currentTool.value =
                toolItems.first { it.title == "brush" }
        },
        shape = RoundedCornerShape(50)
    ) {
        Text(text = "Select")
    }
    OutlinedButton(
        modifier = Modifier.weight(1f),
        onClick = {
            viewModel.revertBrushColor()
            showDialog = false // Dismiss the dialog
        },
        shape = RoundedCornerShape(50)
    ) {
        Text(text = "Cancel")
    }
}
                    }
                }
            }
        }
    }
}

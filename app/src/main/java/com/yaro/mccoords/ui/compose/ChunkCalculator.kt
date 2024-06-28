package com.yaro.mccoords.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaro.mccoords.ChunkMath2d
import com.yaro.mccoords.Point2d
import com.yaro.mccoords.ui.theme.McCoordsTheme

@Composable
fun ChunkCalculator() {
    var block by remember { mutableStateOf(Point2d()) }

    val chunk = ChunkMath2d.blockToChunk(block)
    val chunkMin = ChunkMath2d.chunkToMinBlock(chunk)
    val chunkMax = ChunkMath2d.chunkToMaxBlock(chunk)

    Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 5.dp)) {
        EditablePoint(block) { block = it }
        Spacer(modifier = Modifier.height(12.dp))
        Text("Chunk:     ${chunk.x}, ${chunk.z}")
        Text("Chunk Min: ${chunkMin.x}, ${chunkMin.z}")
        Text("Chunk Max: ${chunkMax.x}, ${chunkMax.z}")
    }
}

@Composable
private fun EditablePoint(pt: Point2d, onValueChange: (Point2d) -> Unit) {
    PointComponent("X", pt.x) { onValueChange(pt.copy(x = it)) }
    PointComponent("Z", pt.z) { onValueChange(pt.copy(z = it)) }
}

@Composable
private fun PointComponent(label: String, value: Int, onValueChange: (Int) -> Unit) {
    val pattern = Regex("^[-+]?\\d*$")
    var textValue by remember {
        mutableStateOf(TextFieldValue(value.toString()))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            Modifier.padding(horizontal = 16.dp)
        )
        OutlinedTextField(
            value = textValue,
            onValueChange = {
                if (pattern.matches(it.text)) {
                    textValue = it
                    onValueChange(it.text.toIntOrNull() ?: 0)
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) {
                    val text = textValue.text
                    textValue = textValue.copy(
                        selection = TextRange(0, text.length)
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChunkCalculatorPreview() {
    McCoordsTheme {
        ChunkCalculator()
    }
}
package com.yaro.mccoords.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaro.mccoords.ChunkMath2d
import com.yaro.mccoords.Point2d
import com.yaro.mccoords.ui.theme.McCoordsTheme

@Composable
fun ChunkCalculator() {
    var block by remember { mutableStateOf(Point2d()) }

    val chunk = ChunkMath2d.blockToChunk(block)

    Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 5.dp)) {
        SectionWithTitle("Block Coordinates") {
            EditablePoint(block) { block = it }
        }

        SectionWithTitle("Chunk Min/Max") {
            ReadOnlyPoint(ChunkMath2d.getChunkMinBlock(chunk))
            ReadOnlyPoint(ChunkMath2d.getChunkMaxBlock(chunk))
        }

        SectionWithTitle("Block Relative Coordinates") {
            ReadOnlyPoint(ChunkMath2d.blockToRelative(block))
        }

        SectionWithTitle("Chunk Coordinates") {
            ReadOnlyPoint(chunk)
        }
    }
}

@Composable
fun SectionWithTitle(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp),
        )
        content()
    }
}


@Composable
private fun EditablePoint(
    point: Point2d,
    onValueChange: (Point2d) -> Unit
) {
    Row {
        val modifier = Modifier.weight(1f)
        PointComponent("X", point.x, modifier = modifier) {
            onValueChange(point.copy(x = it))
        }
        PointComponent("Z", point.z, modifier = modifier) {
            onValueChange(point.copy(z = it))
        }
    }
}

@Composable
private fun PointComponent(
    label: String,
    value: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit
) {
    val pattern = Regex("^[-+]?\\d*$")
    var textValue by remember {
        mutableStateOf(TextFieldValue(value.toString()))
    }

    Surface(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(2.dp),
    ) {
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
            },
            textStyle = TextStyle(textAlign = TextAlign.Center),
            label = {
                Text(
                    label,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
            },
        )
    }
}

@Composable
fun ReadOnlyPoint(
    point: Point2d,
) {
    Row {
        val modifier = Modifier.weight(1f)
        ReadonlyPointComponent(point.x, modifier = modifier)
        ReadonlyPointComponent(point.z, modifier = modifier)
    }
}

@Composable
fun ReadonlyPointComponent(value: Int, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(2.dp),
    ) {
        Text(
            text = value.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(name = "Light", showBackground = true)
//@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ChunkCalculatorPreview() {
    McCoordsTheme {
        ChunkCalculator()
    }
}

@Preview(showBackground = true)
@Composable
fun ReadonlyPointPreview() {
    McCoordsTheme {
        ReadOnlyPoint(Point2d(x = 0, z = 17))
    }
}

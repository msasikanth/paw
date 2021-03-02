/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Breed

data class RowInfo(
    val width: Int,
    val height: Int,
    val nextChildIndex: Int
)

/**
 * Based of: https://gist.github.com/cbeyls/66142cc04348d8246475b730aabef701
 */
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    rowHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
    children: @Composable () -> Unit
) {
    Layout(
        content = children,
        modifier = modifier
    ) { measurables, constraints ->
        val rows = mutableListOf<RowInfo>()

        val maxWidth = constraints.maxWidth

        var contentWidth = 0
        var contentHeight = 0

        var rowWidth = 0
        var rowHeight = 0

        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)
            val newRowWidth = rowWidth + placeable.width

            // When width crosses the max width while placing the placeable
            // switch to next row.
            if (newRowWidth > maxWidth) {
                rows.add(RowInfo(width = rowWidth, height = rowHeight, nextChildIndex = index))

                contentWidth = maxOf(contentWidth, rowWidth)
                contentHeight += rowHeight

                rowWidth = placeable.width
                rowHeight = placeable.height
            } else {
                rowWidth = newRowWidth
                rowHeight = maxOf(rowHeight, placeable.height)
            }

            placeable
        }

        rows.add(RowInfo(width = rowWidth, height = rowHeight, nextChildIndex = measurables.size))

        contentWidth = maxOf(contentWidth, rowWidth)
        contentHeight += rowHeight

        layout(
            width = maxOf(contentWidth, constraints.minWidth),
            height = maxOf(contentHeight, constraints.minHeight)
        ) {
            var childIndex = 0
            var y = 0

            rows.forEach { rowInfo ->
                var x = rowHorizontalAlignment.align(
                    size = constraints.maxWidth - rowInfo.width,
                    space = 0,
                    layoutDirection = LayoutDirection.Ltr
                )

                val nextChildIndex = rowInfo.nextChildIndex
                while (childIndex < nextChildIndex) {
                    val placeable = placeables[childIndex]
                    placeable.placeRelative(
                        x = x,
                        y = y + (rowInfo.height - placeable.height)
                    )

                    x += placeable.width

                    childIndex++
                }
                y += rowInfo.height
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun FlowRowPreview() {
    FlowRow {
        Breed.values().forEach { breed ->
            BreedFilterChip(
                modifier = Modifier.padding(4.dp),
                breed = breed,
                isChecked = false,
                onValueChanged = { /*TODO*/ }
            )
        }
    }
}

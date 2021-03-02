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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Breed
import com.example.androiddevchallenge.ui.theme.MyTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BreedFilterChip(
    modifier: Modifier = Modifier,
    breed: Breed,
    isChecked: Boolean,
    onValueChanged: (Boolean) -> Unit
) {
    val onSurface = MaterialTheme.colors.onSurface
    val strokeColor = if (isChecked) {
        onSurface
    } else {
        onSurface.copy(alpha = 0.12f)
    }
    val borderWidth = if (isChecked) {
        1.5.dp
    } else {
        1.dp
    }

    val border = BorderStroke(
        width = borderWidth,
        color = strokeColor
    )

    Surface(
        modifier = modifier
            .wrapContentWidth()
            .requiredHeight(32.dp),
        color = Color.Transparent,
        shape = RoundedCornerShape(percent = 50),
        border = border
    ) {
        Row(
            Modifier
                .toggleable(
                    value = isChecked,
                    role = Role.Checkbox,
                    onValueChange = onValueChanged
                )
                .padding(start = 4.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(visible = isChecked) {
                val iconTint = MaterialTheme.colors.contentColorFor(MaterialTheme.colors.primarySurface)

                Image(
                    imageVector = Icons.TwoTone.CheckCircle,
                    contentDescription = "${breed.value} is checked",
                    colorFilter = ColorFilter.tint(color = iconTint)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = breed.value,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    heightDp = 64,
    widthDp = 360
)
@Composable
fun BreedFilterChipUnCheckedPreview() {
    MyTheme() {
        BreedFilterChip(
            breed = Breed.GERMAN_SHEPHERD,
            isChecked = false,
            onValueChanged = { }
        )
    }
}

@Preview(
    heightDp = 64,
    widthDp = 360
)
@Composable
fun BreedFilterChipCheckedPreview() {
    MyTheme() {
        Surface {
            BreedFilterChip(
                breed = Breed.GERMAN_SHEPHERD,
                isChecked = true,
                onValueChanged = { }
            )
        }
    }
}

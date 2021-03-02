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
package com.example.androiddevchallenge.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Age
import com.example.androiddevchallenge.data.Gender
import com.example.androiddevchallenge.data.Pet
import com.example.androiddevchallenge.data.pet1
import com.example.androiddevchallenge.ui.components.Chip
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.blue600
import com.example.androiddevchallenge.ui.theme.green600
import com.example.androiddevchallenge.ui.theme.grey200
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun PetGridItem(pet: Pet, onPetSelected: (Pet) -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onPetSelected.invoke(pet) }
    ) {
        Column(
            Modifier
                .padding(all = 12.dp)
                .fillMaxSize()
        ) {
            CoilImage(
                data = pet.photo,
                contentScale = ContentScale.Crop,
                contentDescription = "Image of ${pet.name}",
                fadeIn = true,
                modifier = Modifier
                    .requiredHeight(204.dp)
                    .clip(RoundedCornerShape(8.dp)),
                loading = {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                            )
                            .fillMaxSize()
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = pet.name,
                style = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                GenderChip(gender = pet.gender)
                Spacer(modifier = Modifier.width(8.dp))
                AgeChip(age = pet.age)
            }
        }
    }
}

@Composable
fun GenderChip(gender: Gender) {
    val genderColor = when (gender) {
        Gender.MALE -> blue600.copy(alpha = 0.10f)
        Gender.FEMALE -> green600.copy(alpha = 0.10f)
        Gender.UNSPECIFIED -> grey200
    }
    val genderTextColor = when (gender) {
        Gender.MALE -> blue600
        Gender.FEMALE -> green600
        Gender.UNSPECIFIED -> Color.Black
    }

    Chip(
        text = gender.value,
        color = genderColor,
        textColor = genderTextColor
    )
}

@Composable
fun AgeChip(age: Age) {
    Chip(
        text = age.value,
        color = grey200,
        textColor = Color.Black
    )
}

@Preview
@Composable
fun PetGridItemPreview() {
    MyTheme {
        PetGridItem(pet = pet1, onPetSelected = {})
    }
}

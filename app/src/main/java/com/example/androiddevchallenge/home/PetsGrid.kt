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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Breed
import com.example.androiddevchallenge.data.Pet
import com.example.androiddevchallenge.data.pet1
import com.example.androiddevchallenge.data.pet2
import com.example.androiddevchallenge.data.pet3
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.toPaddingValues

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PetsList(
    filteredBreeds: List<Breed>,
    pets: List<Pet>,
    onPetSelected: (Pet) -> Unit
) {
    val filteredPets = if (filteredBreeds.isNotEmpty()) {
        pets.filter { filteredBreeds.contains(it.breed) }
    } else {
        pets
    }
    val insets = LocalWindowInsets.current
    val bottomPadding = insets.navigationBars.toPaddingValues().calculateBottomPadding()

    val frontLayerShape = RoundedCornerShape(24.dp)

    Surface(
        Modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding),
        shape = frontLayerShape,
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(all = 12.dp),
            content = {
                items(filteredPets.sortedBy { it.name }) { pet ->
                    PetGridItem(pet = pet, onPetSelected = onPetSelected)
                }
            }
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PetsListPreview() {
    MyTheme() {
        PetsList(
            filteredBreeds = emptyList(),
            pets = listOf(pet1, pet2, pet3),
            onPetSelected = {}
        )
    }
}

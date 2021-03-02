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

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ClearAll
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.FilterList
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.androiddevchallenge.data.Breed
import com.example.androiddevchallenge.data.Pet
import com.example.androiddevchallenge.data.pet1
import com.example.androiddevchallenge.data.pet3
import com.example.androiddevchallenge.data.pet5
import com.example.androiddevchallenge.detail.navigateToDetailScreen
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.toPaddingValues
import kotlinx.coroutines.launch

const val HOME_SCREEN_TAG = "HomeScreen"

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    val pets = viewModel.pets.observeAsState(emptyList())
    val filteredBreeds = viewModel.filteredBreeds.observeAsState(emptyList())

    HomeScreenContent(
        pets = pets.value,
        filteredBreeds = filteredBreeds.value,
        onPetSelected = { pet ->
            navController.navigate(navigateToDetailScreen(pet.id))
        },
        onFilterChanged = { breed ->
            viewModel.filterBreed(breed = breed)
        },
        onClearFilters = {
            viewModel.clearFilters()
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    pets: List<Pet>,
    filteredBreeds: List<Breed>,
    onPetSelected: (Pet) -> Unit,
    onFilterChanged: (Breed) -> Unit,
    onClearFilters: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)

    val navigationIconVector = if (scaffoldState.isConcealed) {
        Icons.TwoTone.FilterList
    } else {
        Icons.TwoTone.Close
    }
    val navigationIconContentDescription = if (scaffoldState.isConcealed) {
        "Filter Breeds"
    } else {
        "Close filters"
    }

    val insets = LocalWindowInsets.current

    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            HomeScreenAppBar(
                modifier = Modifier
                    .padding(paddingValues = insets.statusBars.toPaddingValues()),
                navigationIcon = {
                    Icon(
                        imageVector = navigationIconVector,
                        contentDescription = navigationIconContentDescription
                    )
                },
                onNavigationIconClick = {
                    coroutineScope.launch {
                        if (scaffoldState.isConcealed) {
                            scaffoldState.reveal()
                        } else {
                            scaffoldState.conceal()
                        }
                    }
                },
                actions = {
                    if (scaffoldState.isRevealed && filteredBreeds.isNotEmpty()) {
                        IconButton(
                            modifier = Modifier.size(56.dp),
                            onClick = onClearFilters
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.ClearAll,
                                contentDescription = "Clear filters"
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(56.dp))
                    }
                }
            )
        },
        backLayerContent = {
            HomeScreenBreedsFilter(
                filteredBreeds = filteredBreeds,
                onBreedsFilterUpdate = onFilterChanged
            )
        },
        backLayerBackgroundColor = MaterialTheme.colors.primarySurface,
        frontLayerContent = {
            PetsList(
                filteredBreeds = filteredBreeds,
                pets = pets,
                onPetSelected = onPetSelected
            )
        },
        frontLayerShape = RoundedCornerShape(24.dp),
        frontLayerBackgroundColor = Color.Transparent,
        frontLayerElevation = 0.dp,
        peekHeight = 56.dp + insets.statusBars.toPaddingValues().calculateTopPadding()
    )
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    MyTheme {
        HomeScreenContent(
            pets = listOf(pet1, pet3, pet5),
            filteredBreeds = emptyList(),
            onPetSelected = {},
            onFilterChanged = {},
            onClearFilters = {}
        )
    }
}

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
package com.example.androiddevchallenge.detail

import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androiddevchallenge.data.Age
import com.example.androiddevchallenge.data.Gender
import com.example.androiddevchallenge.data.Pet
import com.example.androiddevchallenge.data.pet2
import com.example.androiddevchallenge.ui.components.Chip
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.blue600
import com.example.androiddevchallenge.ui.theme.green600
import com.example.androiddevchallenge.ui.theme.grey200
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.toPaddingValues

const val DETAIL_SCREEN_TAG = "DetailScreen"
const val DETAIL_SCREEN_ARG_PET_ID = "petId"

fun navigateToDetailScreen(id: Int): String {
    return "$DETAIL_SCREEN_TAG/$id"
}

fun getPetId(bundle: Bundle?): Int? {
    return bundle?.getString(DETAIL_SCREEN_ARG_PET_ID)?.toInt()
}

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    petId: Int,
    navController: NavHostController
) {
    LaunchedEffect(
        petId,
        block = {
            viewModel.loadPet(petId)
        }
    )

    val pet = viewModel.pet.observeAsState()

    if (pet.value == null) {
        Surface {
        }

        return
    }

    Surface(
        color = MaterialTheme.colors.primarySurface
    ) {
        DetailContent(pet = pet.value!!)

        BackButton { navController.popBackStack() }
    }
}

@Composable
fun BackButton(onBackClicked: () -> Unit) {
    val insets = LocalWindowInsets.current

    Box(
        modifier = Modifier
            .padding(
                top = insets.statusBars
                    .toPaddingValues()
                    .calculateTopPadding() + 16.dp,
                start = 16.dp
            )
            .shadow(elevation = 2.dp, shape = CircleShape)
            .background(
                color = MaterialTheme.colors.surface,
                shape = CircleShape
            )
    ) {
        IconButton(
            onClick = onBackClicked,
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun DetailContent(pet: Pet) {
    val scrollState = rememberScrollState()
    val insets = LocalWindowInsets.current
    val configuration = LocalConfiguration.current

    val navigationBarPadding = insets
        .navigationBars
        .toPaddingValues()
        .calculateBottomPadding()

    val imageHeight = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 120.dp
        else -> 300.dp
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        CoilImage(
            data = pet.photo,
            contentScale = ContentScale.Crop,
            contentDescription = "Image of ${pet.name}",
            fadeIn = true,
            modifier = Modifier
                .requiredHeight(imageHeight)
                .fillMaxWidth()
        )

        Surface(
            Modifier
                .padding(
                    top = imageHeight - 20.dp,
                    bottom = navigationBarPadding
                )
                .fillMaxSize()
                .verticalScroll(scrollState),
            shape = RoundedCornerShape(24.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    )
            ) {
                Chips(pet = pet)

                Spacer(modifier = Modifier.height(12.dp))

                TitleRow(pet = pet)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = pet.description.orEmpty(),
                    style = MaterialTheme.typography.body2
                )

                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }

    AdoptButton(
        modifier = Modifier
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = navigationBarPadding + 24.dp
            )
    )
}

@Composable
fun Chips(pet: Pet) {
    Row {
        GenderChip(gender = pet.gender)
        Spacer(modifier = Modifier.width(8.dp))
        AgeChip(age = pet.age)
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

@Composable
fun AdoptButton(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.secondary)

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(percent = 50),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            )
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.TwoTone.Pets,
                contentDescription = "Adopt the pet",
                tint = contentColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Adopt me", color = contentColor)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun DetailContentPreview() {
    MyTheme {
        DetailContent(pet = pet2)
    }
}

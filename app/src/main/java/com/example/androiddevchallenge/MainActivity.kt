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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.detail.DETAIL_SCREEN_ARG_PET_ID
import com.example.androiddevchallenge.detail.DETAIL_SCREEN_TAG
import com.example.androiddevchallenge.detail.DetailScreen
import com.example.androiddevchallenge.detail.getPetId
import com.example.androiddevchallenge.home.HOME_SCREEN_TAG
import com.example.androiddevchallenge.home.HomeScreen
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Turn off the decor fitting system windows, which means we need to through handling
        // insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MyTheme {
                ProvideWindowInsets {
                    PawApp()
                }
            }
        }
    }

    @Composable
    fun PawApp() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = HOME_SCREEN_TAG) {
            composable(HOME_SCREEN_TAG) {
                HomeScreen(viewModel = viewModel(), navController = navController)
            }
            composable(
                "$DETAIL_SCREEN_TAG/{$DETAIL_SCREEN_ARG_PET_ID}"
            ) {
                val petId = getPetId(it.arguments) ?: throw IllegalArgumentException("Failed to get the argument")
                DetailScreen(viewModel = viewModel(), navController = navController, petId = petId)
            }
        }
    }
}

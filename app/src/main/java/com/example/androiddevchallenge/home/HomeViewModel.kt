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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.data.Breed
import com.example.androiddevchallenge.data.Pet
import com.example.androiddevchallenge.data.PetRepository

class HomeViewModel : ViewModel() {

    private val petRepository = PetRepository()

    private val _pets = MutableLiveData<List<Pet>>()
    val pets: LiveData<List<Pet>>
        get() = _pets

    private val _filteredBreeds = MutableLiveData<List<Breed>>()
    val filteredBreeds: LiveData<List<Breed>>
        get() = _filteredBreeds

    init {
        _pets.postValue(petRepository.petsList())
    }

    fun filterBreed(breed: Breed) {
        val breeds = filteredBreeds.value.orEmpty().toMutableList()
        if (breeds.contains(breed)) {
            breeds.remove(breed)
        } else {
            breeds.add(breed)
        }

        _filteredBreeds.postValue(breeds)
    }

    fun clearFilters() {
        _filteredBreeds.postValue(emptyList())
    }
}

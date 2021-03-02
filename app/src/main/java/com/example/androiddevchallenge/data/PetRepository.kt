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
package com.example.androiddevchallenge.data

private const val lipsum =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla in viverra nisi. Nunc gravida interdum dui, a dignissim metus sagittis ut. Donec accumsan ornare efficitur. Vestibulum ac risus congue, malesuada lacus rhoncus, varius augue. Aenean massa erat, porta a libero at, facilisis aliquam risus. Maecenas commodo fermentum tempor. Cras id ex euismod massa fringilla ornare. Ut sed justo lorem. Suspendisse vel nisl ac massa scelerisque placerat. Morbi magna mauris, laoreet eu consectetur id, cursus vel lectus."

val pet1 = Pet(
    id = 1,
    name = "Ashoka",
    description = lipsum,
    breed = Breed.AUSTRALIAN_SHEPHERD,
    gender = Gender.FEMALE,
    age = Age.PUPPY,
    photo = "https://images.unsplash.com/photo-1609877026418-a478f480a9c8?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80"
)

val pet2 = Pet(
    id = 2,
    name = "Scout",
    description = lipsum,
    breed = Breed.BORDER_COLLIE,
    gender = Gender.MALE,
    age = Age.ADULT,
    photo = "https://images.unsplash.com/photo-1487341290491-1081724e5844?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80"
)

val pet3 = Pet(
    id = 3,
    name = "Landon",
    description = lipsum,
    breed = Breed.BULL_TERRIER,
    gender = Gender.UNSPECIFIED,
    age = Age.ADULT,
    photo = "https://images.unsplash.com/photo-1531860588597-5f93b7296082?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80"
)

val pet4 = Pet(
    id = 4,
    name = "Peaches",
    description = lipsum,
    breed = Breed.CORGI,
    gender = Gender.FEMALE,
    age = Age.YOUNG,
    photo = "https://images.unsplash.com/photo-1507449863419-a3aa39ac22d3?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80"
)

val pet5 = Pet(
    id = 5,
    name = "Bandit",
    description = lipsum,
    breed = Breed.DALMATION,
    gender = Gender.MALE,
    age = Age.PUPPY,
    photo = "https://images.unsplash.com/photo-1562771968-a70d17a93823?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80"
)

val pet6 = Pet(
    id = 6,
    name = "Titan",
    description = lipsum,
    breed = Breed.DOBERMAN_PINSCHER,
    gender = Gender.MALE,
    age = Age.SENIOR,
    photo = "https://images.unsplash.com/photo-1571167143301-3e6eb056e8e0?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80"
)

val pet7 = Pet(
    id = 7,
    name = "Cassia",
    description = lipsum,
    breed = Breed.GERMAN_SHEPHERD,
    gender = Gender.FEMALE,
    age = Age.YOUNG,
    photo = "https://images.unsplash.com/photo-1588160073354-58c814c0e64b?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1955&q=80"
)

val pet8 = Pet(
    id = 8,
    name = "Charlie",
    description = lipsum,
    breed = Breed.PUG,
    gender = Gender.MALE,
    age = Age.YOUNG,
    photo = "https://images.unsplash.com/photo-1535909339361-ef56e179d637?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80"
)

class PetRepository {

    private val pets = listOf(
        pet1,
        pet2,
        pet3,
        pet4,
        pet5,
        pet6,
        pet7,
        pet8
    )

    fun petsList(): List<Pet> {
        return pets
    }

    fun pet(id: Int): Pet? {
        return pets.firstOrNull { it.id == id }
    }
}

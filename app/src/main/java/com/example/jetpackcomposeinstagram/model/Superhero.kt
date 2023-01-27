package com.example.jetpackcomposeinstagram.model

import androidx.annotation.DrawableRes

data class Superhero(
    val name: String,
    val realName: String,
    val publisher: String,
    @DrawableRes var photo: Int
)
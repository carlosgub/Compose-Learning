package com.example.jetpackcomposeinstagram.model

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.jetpackcomposeinstagram.*
import com.example.jetpackcomposeinstagram.instagram.ui.InstagramLoginScreen
import com.example.jetpackcomposeinstagram.instagram.ui.InstagramLoginViewModel

class Screen(
    private val navController: NavController,
    private val instagramLoginViewModel: InstagramLoginViewModel
) {

    data class ScreenModel(
        val navigation_name: String,
        val screenName: String,
        val composeScreen: @Composable (NavBackStackEntry) -> Unit
    )

    fun getScreens(): MutableList<ScreenModel> =
        mutableListOf<ScreenModel>().apply {
            add(ScreenModel(
                navigation_name = "home",
                screenName = "home",
                composeScreen = {
                    HomeScreen(navController, instagramLoginViewModel)
                }
            ))
            add(ScreenModel(
                navigation_name = "instagram",
                screenName = "Instagram login",
                composeScreen = {
                    InstagramLoginScreen(navController, instagramLoginViewModel)
                }
            ))
            add(ScreenModel(
                navigation_name = "twitter",
                screenName = "Twitter Tweet",
                composeScreen = {
                    TweetScreen(navController)
                }
            ))
            add(ScreenModel(
                navigation_name = "rv",
                screenName = "RecyclerView",
                composeScreen = {
                    SuperHeroesViewScreen(navController)
                }
            ))
            add(ScreenModel(
                navigation_name = "sticky_rv",
                screenName = "Sticky Recyclerview",
                composeScreen = {
                    StickySuperHeroesViewScreen(navController)
                }
            ))
            add(ScreenModel(
                navigation_name = "scaffold",
                screenName = "Scaffold example",
                composeScreen = {
                    ScaffoldExampleScreen(navController)
                }
            ))
        }
}
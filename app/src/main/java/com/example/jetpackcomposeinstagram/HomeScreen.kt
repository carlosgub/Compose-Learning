package com.example.jetpackcomposeinstagram

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.jetpackcomposeinstagram.instagram.ui.InstagramLoginViewModel
import com.example.jetpackcomposeinstagram.model.Screen

@Composable
fun HomeScreen(navController: NavController, viewModel: InstagramLoginViewModel) {
    ConstraintLayout(Modifier.fillMaxSize()) {

        val (rv) = createRefs()

        LazyColumn(modifier = Modifier.constrainAs(rv) {
            linkTo(start = parent.start, end = parent.end)
            linkTo(top = parent.top, bottom = parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }) {
            item {
                Divider()
            }
            items(getItems(navController, viewModel)) {
                HomeItem(it) { screenRoute ->
                    navController.navigate(screenRoute)
                }
            }
        }
    }
}

private fun getItems(
    navController: NavController,
    viewModel: InstagramLoginViewModel
): List<Pair<String, String>> =
    Screen(navController, viewModel).getScreens().drop(1).map {
        Pair(it.navigation_name, it.screenName)
    }

@Composable
private fun HomeItem(info: Pair<String, String>, screenRoute: (String) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                screenRoute(info.first)
            }
    ) {

        val (text, divider, icon) = createRefs()

        Text(
            text = info.second,
            modifier = Modifier

                .constrainAs(text) {
                    linkTo(
                        start = parent.start,
                        startMargin = 16.dp,
                        end = icon.end,
                        endMargin = 8.dp
                    )
                    top.linkTo(parent.top, 16.dp)
                    width = Dimension.fillToConstraints
                }
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Arrow",
            modifier = Modifier.constrainAs(icon) {
                linkTo(top = parent.top, bottom = parent.bottom)
                end.linkTo(parent.end, 16.dp)
            }
        )
        Divider(
            modifier = Modifier.constrainAs(divider) {
                linkTo(top = text.bottom, topMargin = 16.dp, bottom = parent.bottom)
            }
        )
    }
}
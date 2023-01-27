package com.example.jetpackcomposeinstagram

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.jetpackcomposeinstagram.model.Superhero

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickySuperHeroesViewScreen(navController: NavController) {
    val context = LocalContext.current
    val rvState = rememberLazyListState()
    ConstraintLayout(
        modifier =
        Modifier.fillMaxSize()
    ) {
        val (rv, button, rvGrid) = createRefs()
        LazyColumn(
            state = rvState,
            modifier = Modifier.constrainAs(rv) {
                start.linkTo(parent.start, 8.dp)
                end.linkTo(parent.end)
                top.linkTo(parent.top, 16.dp)
            },
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            val items: Map<String, List<Superhero>> = getSuperheroes().groupBy { it.publisher }
            items.forEach { (publisher, list) ->
                stickyHeader {
                    Text(
                        text = publisher,
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                    )

                }
                items(list) {
                    stickyItemHero(it) {
                        Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

@Composable
fun stickyItemHero(hero: Superhero, onItemSelected: (Superhero) -> Unit) {
    Card(
        border = BorderStroke(width = 2.dp, color = Color.Red),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected(hero) }
    ) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (image, name, realName, publisher) = createRefs()

            Image(
                painter = painterResource(id = hero.photo),
                contentDescription = "Hero photo",
                modifier = Modifier.constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        startMargin = 36.dp,
                        end = parent.end,
                        endMargin = 36.dp
                    )
                    top.linkTo(parent.top, 16.dp)
                    width = Dimension.fillToConstraints
                },
                contentScale = ContentScale.Crop
            )

            Text(text = hero.name, modifier = Modifier.constrainAs(name) {
                top.linkTo(image.bottom, 8.dp)
                linkTo(
                    start = parent.start,
                    startMargin = 36.dp,
                    end = parent.end,
                    endMargin = 36.dp
                )
            })

            Text(text = hero.realName, modifier = Modifier.constrainAs(realName) {
                top.linkTo(name.bottom, 4.dp)
                linkTo(
                    start = parent.start,
                    startMargin = 36.dp,
                    end = parent.end,
                    endMargin = 36.dp
                )
            })

            Text(text = hero.publisher, modifier = Modifier.constrainAs(publisher) {
                linkTo(
                    top = realName.bottom,
                    topMargin = 4.dp,
                    bottom = parent.bottom,
                    bottomMargin = 16.dp
                )
                linkTo(
                    start = parent.start,
                    startMargin = 36.dp,
                    end = parent.end,
                    endMargin = 36.dp
                )
            })
        }
    }
}
package com.example.jetpackcomposeinstagram

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuperHeroesViewScreen(navController: NavController) {
    val context = LocalContext.current
    val rvState = rememberLazyListState()
    val rvGridState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    ConstraintLayout(
        modifier =
        Modifier.fillMaxSize()
    ) {
        val (rv, button, rvGrid) = createRefs()
        LazyRow(
            state = rvState,
            modifier = Modifier.constrainAs(rv) {
                start.linkTo(parent.start, 8.dp)
                end.linkTo(parent.end)
                top.linkTo(parent.top, 16.dp)
            },
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            items(getSuperheroes()) {
                ItemHero(it) {
                    Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
                }
            }
        }

        /*region Button*/
        val showButton by remember {
            derivedStateOf {
                rvState.firstVisibleItemIndex > 0 || rvGridState.firstVisibleItemScrollOffset > 0
            }
        }

        if (showButton) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        rvState.animateScrollToItem(0)
                        rvGridState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.constrainAs(button) {
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    top.linkTo(rv.bottom, 16.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                Text("Boton para hacer scroll al item 0")
            }
        }
        /*endregion*/

        val barrier = createBottomBarrier(button, rv)

        LazyVerticalGrid(
            state = rvGridState,
            cells = GridCells.Fixed(2),
            modifier = Modifier.constrainAs(rvGrid) {
                start.linkTo(parent.start, 8.dp)
                end.linkTo(parent.end, 8.dp)
                top.linkTo(barrier, 16.dp)
            }
        ) {
            items(getSuperheroes()) {
                ItemHero(it) {
                    Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Composable
fun ItemHero(hero: Superhero, onItemSelected: (Superhero) -> Unit) {
    Card(
        border = BorderStroke(width = 2.dp, color = Color.Red),
        modifier = Modifier
            .width(200.dp)
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

fun getSuperheroes(): List<Superhero> =
    listOf(
        Superhero(
            name = "Spiderman",
            realName = "Petter Parker",
            publisher = "Marvel",
            R.drawable.spiderman
        ),
        Superhero(
            name = "Wolverine",
            realName = "James Howlett",
            publisher = "Marvel",
            R.drawable.logan
        ),
        Superhero(name = "Batman", realName = "Bruce Wayne", publisher = "DC", R.drawable.batman),
        Superhero(name = "Thor", realName = "Thor Odinson", publisher = "Marvel", R.drawable.thor),
        Superhero(name = "Flash", realName = "Jay Garrick", publisher = "DC", R.drawable.flash),
        Superhero(
            name = "Green Lantern",
            realName = "Alan Scott",
            publisher = "DC",
            R.drawable.green_lantern
        ),
        Superhero(
            name = "Wonder Woman",
            realName = "Princess Diana",
            publisher = "DC",
            R.drawable.wonder_woman
        )

    )
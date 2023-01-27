package com.example.jetpackcomposeinstagram

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.jetpackcomposeinstagram.ui.theme.InstaBlueColor
import kotlinx.coroutines.launch

@Composable
fun ScaffoldExampleScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopBar(onClickIcon = {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Has pulsado $it")
                }
            }, openDrawer = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            })
        },
        scaffoldState = scaffoldState,
        bottomBar = {
            MyBottomNavigation()
        },
        floatingActionButton = {
            myFAB()
        },
        drawerGesturesEnabled = false,
        drawerContent = {
            myDrawer {
                coroutineScope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        }
    ) {

    }
}

@Composable
fun MyTopBar(onClickIcon: (String) -> Unit, openDrawer: () -> Unit) {
    TopAppBar(
        title = { Text("Toolbar") },
        backgroundColor = InstaBlueColor,
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(
                onClick = { onClickIcon("Atras") }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Atras"
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onClickIcon("Buscar") }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar"
                )
            }
            IconButton(
                onClick = { openDrawer() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Drawer"
                )
            }
        }
    )
}

@Composable
fun MyBottomNavigation() {
    var index by remember {
        mutableStateOf(0)
    }
    BottomNavigation(
        backgroundColor = InstaBlueColor,
        contentColor = Color.White
    ) {
        BottomNavigationItem(selected = index == 0,
            onClick = { index = 0 },
            icon = {
                Icon(
                    imageVector = Icons.Filled.House,
                    contentDescription = "Home"
                )
            },
            label = {
                Text("Home")
            })
        BottomNavigationItem(selected = index == 1,
            onClick = { index = 1 },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favoritos"
                )
            },
            label = {
                Text("Favoritos")
            })
        BottomNavigationItem(selected = index == 2,
            onClick = { index = 2 },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text("Perfil")
            })
    }
}

@Composable
fun myFAB() {
    FloatingActionButton(
        onClick = { },
        backgroundColor = InstaBlueColor,
        contentColor = Color.White
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Agregar")
    }
}

@Composable
fun myDrawer(closeDrawer: () -> Unit) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (rv) = createRefs()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(rv) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(5) {
                item {
                    Text("Opcion $it", modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            closeDrawer()
                        })
                }
            }
        }
    }
}
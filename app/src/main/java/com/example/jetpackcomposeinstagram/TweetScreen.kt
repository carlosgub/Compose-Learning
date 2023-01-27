package com.example.jetpackcomposeinstagram

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.jetpackcomposeinstagram.ui.theme.TwitterBackgroundColor

@Composable
fun TweetScreen(navController: NavController) {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(TwitterBackgroundColor)
    ) {
        val (photoImage, user, username, time, dots, tweetText, tweetImage, constraintComment, constraintShare, constraintLove, divider) = createRefs()
        val startGuide = createGuidelineFromStart(24.dp)
        val topGuide = createGuidelineFromTop(24.dp)
        val endGuide = createGuidelineFromEnd(24.dp)
        var commentClickeable by rememberSaveable { mutableStateOf(false) }
        var shareClickeable by rememberSaveable { mutableStateOf(false) }
        var loveClickeable by rememberSaveable { mutableStateOf(false) }

        /*region Top Views Tweet*/
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile photo",
            modifier = Modifier
                .size(60.dp)
                .constrainAs(photoImage) {
                    start.linkTo(startGuide)
                    top.linkTo(topGuide)
                }
                .clip(CircleShape)
        )

        /*region Top User Info*/
        Text(text = "Aris",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .wrapContentWidth(Alignment.Start)
                .constrainAs(user) {
                    start.linkTo(photoImage.end, margin = 8.dp)
                    top.linkTo(topGuide)
                }
        )

        Text(text = "@AristiDevs",
            color = Color.Gray,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .constrainAs(username) {
                    top.linkTo(user.top)
                    linkTo(
                        start = user.end,
                        startMargin = 8.dp,
                        end = time.start,
                        endMargin = 8.dp,
                        bias = 0F
                    )
                    width = Dimension.fillToConstraints
                }
        )

        Text(text = "4h",
            color = Color.Gray,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .constrainAs(time) {
                    top.linkTo(user.top)
                    end.linkTo(dots.start, 8.dp)
                }
        )
//        createHorizontalChain(user,username,time, chainStyle = ChainStyle.Packed(0F))
        /*endregion*/

        Image(
            painter = painterResource(id = R.drawable.ic_dots),
            contentDescription = "dots",
            modifier = Modifier
                .size(32.dp)
                .constrainAs(dots) {
                    end.linkTo(endGuide)
                    top.linkTo(user.top)
                    bottom.linkTo(user.bottom)
                },
            colorFilter = ColorFilter.tint(Color.White)
        )
        /*endregion*/

        /*region Tweet*/
        Text(
            text = "Descipcion larga Descipcion larga Descipcion larga Descipcion largaDescipcion larga Descipcion larga Descipcion larga Descipcion largaDescipcion larga",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.constrainAs(tweetText) {
                top.linkTo(user.bottom, margin = 8.dp)
                linkTo(
                    start = photoImage.end,
                    startMargin = 8.dp,
                    end = endGuide
                )
                width = Dimension.fillToConstraints
            }
        )

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "tweetImage",
            modifier = Modifier
                .clip(CircleShape.copy(CornerSize(24.dp)))
                .height(200.dp)
                .constrainAs(tweetImage) {
                    top.linkTo(tweetText.bottom, margin = 12.dp)
                    linkTo(
                        start = photoImage.end,
                        startMargin = 8.dp,
                        end = endGuide
                    )
                    width = Dimension.fillToConstraints
                },
            contentScale = ContentScale.Crop
        )
        /*endregion*/

        /*region Share*/
        ConstraintLayout(Modifier
            .clickable {
                commentClickeable = !commentClickeable
            }
            .fillMaxWidth(1f)
            .constrainAs(constraintComment) {
                top.linkTo(tweetImage.bottom, margin = 16.dp)
                start.linkTo(tweetImage.start)
                end.linkTo(constraintShare.start)
                width = Dimension.fillToConstraints
            }) {
            val (comment, number) = createRefs()
            Image(
                painter = painterResource(
                    id = if (commentClickeable) {
                        R.drawable.ic_chat_filled
                    } else {
                        R.drawable.ic_chat
                    }
                ),
                contentDescription = "comments in tweet",
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = Modifier
                    .size(28.dp)
                    .constrainAs(comment) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )
            Text(
                text = if (commentClickeable) {
                    "2"
                } else {
                    "1"
                },
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.constrainAs(number) {
                    start.linkTo(comment.end, margin = 8.dp)
                    top.linkTo(comment.top)
                    bottom.linkTo(comment.bottom)
                })
        }

        ConstraintLayout(Modifier
            .clickable {
                shareClickeable = !shareClickeable
            }
            .fillMaxWidth(1f)
            .constrainAs(constraintShare) {
                top.linkTo(tweetImage.bottom, margin = 16.dp)
                start.linkTo(constraintComment.end, margin = 12.dp)
                end.linkTo(constraintLove.start)
                width = Dimension.fillToConstraints
            }) {
            val (share, number) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ic_rt),
                contentDescription = "comments in tweet",
                colorFilter = ColorFilter.tint(
                    if (shareClickeable) {
                        Color.Green
                    } else {
                        Color.Gray
                    }
                ),
                modifier = Modifier
                    .size(28.dp)
                    .constrainAs(share) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )
            Text(
                text = if (shareClickeable) {
                    "2"
                } else {
                    "1"
                },
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.constrainAs(number) {
                    start.linkTo(share.end, margin = 8.dp)
                    top.linkTo(share.top)
                    bottom.linkTo(share.bottom)
                })
        }
        ConstraintLayout(Modifier
            .clickable {
                loveClickeable = !loveClickeable
            }
            .fillMaxWidth(1f)
            .constrainAs(constraintLove) {
                top.linkTo(tweetImage.bottom, margin = 16.dp)
                start.linkTo(constraintShare.end, margin = 12.dp)
                end.linkTo(tweetImage.end)
                width = Dimension.fillToConstraints
            }) {
            val (love, number) = createRefs()
            Image(
                painter = painterResource(
                    id =
                    if (loveClickeable) {
                        R.drawable.ic_like_filled
                    } else {
                        R.drawable.ic_like
                    }
                ),
                contentDescription = "comments in tweet",
                colorFilter = ColorFilter.tint(
                    if (loveClickeable) {
                        Color.Red
                    } else {
                        Color.Gray
                    }
                ),
                modifier = Modifier
                    .size(28.dp)
                    .constrainAs(love) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )
            Text(
                text = if (loveClickeable) {
                    "2"
                } else {
                    "1"
                },
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.constrainAs(number) {
                    start.linkTo(love.end, margin = 8.dp)
                    top.linkTo(love.top)
                    bottom.linkTo(love.bottom)
                })
        }
        /*endregion*/

        /*region Footer*/
        Divider(
            modifier = Modifier.constrainAs(divider) {
                top.linkTo(constraintComment.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
            },
            thickness = 0.5.dp,
            color = Color.Gray
        )
        /*endregion*/
    }
}


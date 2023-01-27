package com.example.jetpackcomposeinstagram.instagram.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.jetpackcomposeinstagram.R
import com.example.jetpackcomposeinstagram.ui.theme.InstaBlueColor
import com.example.jetpackcomposeinstagram.ui.theme.TextFieldBackgroundColor
import com.example.jetpackcomposeinstagram.ui.theme.TextFieldTextColor

@Composable
fun InstagramLoginScreen(navController: NavController, viewModel: InstagramLoginViewModel) {

    val context = LocalContext.current as Activity
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        /*region Header*/
        val (closeIcon) = createRefs()
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close App",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    context.finish()
                }
                .constrainAs(closeIcon) {
                    top.linkTo(parent.top, margin = 16.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                },
        )
        /*endregion*/

        /*region Body*/
        val (logo, userTextField, passwordTextField, recoverPasswordText, loginButton) = createRefs()
        val (firstOrDivider, orText, secondOrDivider) = createRefs()
        val (fbImageIcon, fbText) = createRefs()
        val (footerSeparator, footerTextFirst, footerTextSecond) = createRefs()

        val userText by viewModel.userField.observeAsState(initial = "")
        val passwordText by viewModel.passwordField.observeAsState(initial = "")
        val isLoginEnable: Boolean by viewModel.isLoginEnabled.observeAsState(initial = false)
        val ga = viewModel.loginMessage.observeAsState(initial = "")
        if(ga.value.isNotBlank()){
            Toast.makeText(context, ga.value, Toast.LENGTH_LONG).show()
        }

        val startGuide = createGuidelineFromStart(12.dp)
        val endGuide = createGuidelineFromEnd(12.dp)
        /*region Logo*/
        Image(
            painter = painterResource(id = R.drawable.instagram_logo),
            contentDescription = "Instagram Logo",
            modifier = Modifier
                .padding(start = 100.dp, end = 100.dp)
                .constrainAs(logo) {
                    top.linkTo(closeIcon.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(userTextField.top)
                }
        )
        /*endregion*/

        /*region TextFields*/
        TextField(
            value = userText,
            onValueChange = {
                viewModel.userFieldChange(it)
            },
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 12.sp
            ),
            placeholder = {
                Text(
                    text = "Phone number, username or email",
                    fontSize = 12.sp,
                    color = TextFieldTextColor
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = TextFieldBackgroundColor,
                textColor = TextFieldTextColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(userTextField) {
                    top.linkTo(logo.bottom)
                    end.linkTo(endGuide)
                    start.linkTo(startGuide)
                    bottom.linkTo(passwordTextField.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(56.dp)
                }
        )

        var passwordIcon by rememberSaveable { mutableStateOf(true) }
        TextField(
            value = passwordText,
            onValueChange = {
                viewModel.passwordFieldChange(it)
            },
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 12.sp
            ),
            placeholder = {
                Text(
                    text = "Password",
                    fontSize = 12.sp,
                    color = TextFieldTextColor
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = TextFieldBackgroundColor,
                textColor = TextFieldTextColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .constrainAs(passwordTextField) {
                    top.linkTo(userTextField.bottom)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    bottom.linkTo(recoverPasswordText.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(56.dp)
                },
            trailingIcon = {
                Icon(
                    imageVector = if (passwordIcon) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = "Mostrar contraseña",
                    modifier = Modifier.clickable {
                        passwordIcon = !passwordIcon
                    })
            },
            visualTransformation = if (passwordIcon) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            }
        )
        /*endregion*/

        /*region Forgot Password Text*/
        Text(
            text = "Forgot Password",
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(recoverPasswordText) {
                    top.linkTo(passwordTextField.bottom)
                    end.linkTo(endGuide)
                    bottom.linkTo(loginButton.top)
                },
            color = InstaBlueColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        /*endregion*/

        /*region Login Button*/
        Button(
            onClick = { viewModel.login() },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = InstaBlueColor,
                disabledBackgroundColor = InstaBlueColor.copy(
                    alpha = 0.5f
                ),
                disabledContentColor = Color.White
            ),
            enabled = isLoginEnable,
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(loginButton) {
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    top.linkTo(recoverPasswordText.bottom)
                    bottom.linkTo(firstOrDivider.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(48.dp)
                }
        ) {
            Text(text = "Log in")
        }
        /*endregion*/

        /*region OR Text*/
        Divider(
            thickness = 0.5.dp,
            modifier = Modifier
                .padding(top = 24.dp, bottom = 24.dp)
                .constrainAs(firstOrDivider) {
                    start.linkTo(startGuide)
                    end.linkTo(orText.start)
                    top.linkTo(loginButton.bottom)
                    bottom.linkTo(fbText.top)
                    width = Dimension.fillToConstraints
                })

        Text(
            text = "OR",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(orText) {
                    start.linkTo(firstOrDivider.end)
                    end.linkTo(secondOrDivider.start)
                    top.linkTo(firstOrDivider.top)
                    bottom.linkTo(firstOrDivider.bottom)
                })

        Divider(
            thickness = 0.5.dp,
            modifier = Modifier
                .constrainAs(secondOrDivider) {
                    end.linkTo(endGuide)
                    start.linkTo(orText.end)
                    top.linkTo(firstOrDivider.top)
                    bottom.linkTo(firstOrDivider.bottom)
                    width = Dimension.fillToConstraints
                })
        /*endregion*/

        /*region FB Login*/
        Image(
            painter = painterResource(id = R.drawable.fb),
            colorFilter = ColorFilter.tint(InstaBlueColor),
            contentDescription = "",
            modifier = Modifier
                .size(16.dp)
                .constrainAs(fbImageIcon) {
                    top.linkTo(fbText.top)
                    start.linkTo(startGuide)
                    end.linkTo(fbText.start)
                    bottom.linkTo(fbText.bottom)
                }
        )
        Text(
            text = "Continue as Dave johnson",
            color = InstaBlueColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                .constrainAs(fbText) {
                    top.linkTo(orText.top)
                    start.linkTo(fbImageIcon.end)
                    end.linkTo(endGuide)
                    bottom.linkTo(footerSeparator.top)
                }
        )
        createHorizontalChain(fbImageIcon, fbText, chainStyle = ChainStyle.Packed)
        /*endregion*/


        createVerticalChain(
            logo,
            userTextField,
            passwordTextField,
            recoverPasswordText,
            loginButton,
            firstOrDivider,
            fbText,
            chainStyle = ChainStyle.Packed
        )

        /*endregion*/

        /*region Footer*/
        Divider(thickness = 0.5.dp, modifier = Modifier
            .fillMaxWidth()
            .constrainAs(footerSeparator) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(footerTextFirst.top, margin = 16.dp)
            })
        Text(text = "Don't have an account?",
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.constrainAs(footerTextFirst) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(footerTextSecond.start)
            })
        Text(text = "Sign Up.",
            color = InstaBlueColor,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 4.dp)
                .constrainAs(footerTextSecond) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(parent.end)
                    start.linkTo(footerTextFirst.end)
                })

        createHorizontalChain(footerTextFirst, footerTextSecond, chainStyle = ChainStyle.Packed)
        /*endregion*/
    }
}

package com.example.roomapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.roomapp.data.User
import com.example.roomapp.data.UserViewModel
import com.example.roomapp.ui.composeFunctions.TopActionBar
import com.example.roomapp.ui.route.SharedViewModel

@Composable
fun UpdateDataBtn(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Update Data")
    }
}

@Composable
fun DeleteDataBtn(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Delete Data")
    }
}
@Composable
fun DeleteAllDataBtn(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Delete All Data")
    }
}
@Composable
fun UpdateDetailsScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val userShared = sharedViewModel.userShared
    val userViewModel: UserViewModel = viewModel()


    var firstName by remember { mutableStateOf(userShared?.firstName.orEmpty()) }
    var lastName by remember { mutableStateOf(userShared?.lastName.orEmpty()) }
    var position by remember { mutableStateOf(userShared?.position.orEmpty()) }
    var age by remember { mutableIntStateOf(userShared?.age ?: 0) }
    var rating by remember { mutableIntStateOf(userShared?.rating ?: 0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopActionBar(navController = navController, actionBarName = "Update/Delete Details")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // The it keyword is used as a default name for the single parameter of the lambda function.
            // In this context, it refers to the new text value that is passed as a parameter to the lambda function
            TextField("First Name", firstName) { newValue -> firstName = newValue }
            TextField("Last Name", lastName) { newValue -> lastName = newValue }
            NumberTextField(
                labelName = "Age",
                numVar = age,
                onValueChange = { newValue -> age = newValue })
            TextField("Position", position) { newValue -> position = newValue }
            NumberTextField(
                labelName = "Rating",
                numVar = rating,
                onValueChange = { newValue -> rating = newValue })
            UpdateDataBtn {
                val updatedUser =
                    userShared?.id?.let { User(it, firstName, lastName, age, position, rating) }
                if (updatedUser != null) userViewModel.updateUser(updatedUser)
            }
            DeleteDataBtn {
                val delUser =
                    userShared?.id?.let { User(it, firstName, lastName, age, position, rating) }
                if (delUser != null) userViewModel.deleteUser(delUser)
            }
            DeleteAllDataBtn {
                userViewModel.deleteAllUser()
                userViewModel.resestPrimaryKey()
            }
        }

    }
}




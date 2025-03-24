package com.example.coroutinesdemo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchBar()
        }
    }
}

@Composable
fun SearchBar() {
    val namesList = listOf("Esraa", "Rana", "Ayah", "Reham", "Ayatullah")

    val searchQuery = remember { mutableStateOf("") }
    val sharedFlow = remember { MutableSharedFlow<String>(replay = 1) }
    val coroutineScope = rememberCoroutineScope()
    val filteredList = remember { mutableStateOf(namesList) }

    LaunchedEffect(Unit) {
        sharedFlow
            .collect { searchQuery ->
                filteredList.value = if (searchQuery.isEmpty()) {
                    namesList
                } else {
                    namesList.filter { it.startsWith(searchQuery, ignoreCase = true) }
                }
            }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = searchQuery.value,
            onValueChange = {
                searchQuery.value = it
                coroutineScope.launch { sharedFlow.emit(it) }
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        filteredList.value.forEach { name ->
            Text(text = name, modifier = Modifier.padding(4.dp))
        }
    }
}
package com.example.coroutinesdemo2

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    val sharedState = MutableStateFlow(0)
    launch {
        sharedState.collect {
            println(it)
        }
    }

    sharedState.emit(1)
    sharedState.emit(2)
    sharedState.emit(3)
}
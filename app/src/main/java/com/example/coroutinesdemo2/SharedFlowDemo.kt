package com.example.coroutinesdemo2

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {

    val sharedFlow = MutableSharedFlow<Int>(replay = 1)

    launch {
        sharedFlow.collect {
            println(it)
        }
    }

    delay(200)
    sharedFlow.emit(1)
    sharedFlow.emit(2)
    sharedFlow.emit(3)
}
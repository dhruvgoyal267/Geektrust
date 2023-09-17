package com.assignments.geektrust.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object DispatcherProvider {

    val IO: CoroutineDispatcher = Dispatchers.IO

    val Main: CoroutineDispatcher = Dispatchers.Main

    val Default: CoroutineDispatcher = Dispatchers.Default

    val UnConfined: CoroutineDispatcher = Dispatchers.Unconfined
}
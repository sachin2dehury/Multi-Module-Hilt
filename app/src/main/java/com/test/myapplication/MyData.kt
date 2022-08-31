package com.test.myapplication

import java.util.*

sealed class MyData {
    data class X(
        val value: String = UUID.randomUUID().toString()
    ) : MyData()

    data class Y(
        val value: Int = 0
//        val value2: String = UUID.randomUUID().toString()
    ) : MyData()
}

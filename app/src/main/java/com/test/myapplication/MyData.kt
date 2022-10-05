package com.test.myapplication

import java.util.*

sealed class MyData {
    data class X(
        val value: String = UUID.randomUUID().toString(),
        val data: ABC = ABC.A()
    ) : MyData()

    data class Y(
        val value: Int = 0
//        val value2: String = UUID.randomUUID().toString()
    ) : MyData()
}

sealed class ABC {
    data class A(val data: String = UUID.randomUUID().toString()) : ABC()
    data class B(val data: String = UUID.randomUUID().toString()) : ABC()
}

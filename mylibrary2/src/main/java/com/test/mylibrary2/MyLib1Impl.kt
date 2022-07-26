package com.test.mylibrary2

import com.test.mylibrary1.MyLib1

class MyLib1Impl : MyLib1 {
    override fun get(): String {
        return "com.test.mylibrary2"
    }
}

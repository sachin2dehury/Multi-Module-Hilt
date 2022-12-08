package com.test.mylibrary3

import com.test.mylibrary1.MyLib1
import com.test.mylibrary4.MyLib4
import javax.inject.Inject

class MyLib4Impl @Inject constructor(private val lib1: MyLib1) : MyLib4 {
    override fun get(): String {
        return "com.test.mylibrary3"
    }

    override fun getLib1(): String {
        return lib1.get()
    }
}

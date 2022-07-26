package com.test.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.mylibrary1.MyLib1
import com.test.mylibrary4.MyLib4
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var myLib1: MyLib1

    @Inject
    lateinit var myLib4: MyLib4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.w("Sachin", "${myLib1.get()}, ${myLib4.get()}, ${myLib4.getLib1()}")
    }
}

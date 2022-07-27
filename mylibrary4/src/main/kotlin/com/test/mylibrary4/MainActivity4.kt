package com.test.mylibrary4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity4 : AppCompatActivity() {

    @Inject
    lateinit var lib4: MyLib4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        Log.w("Sachin", "${lib4.get()} ${lib4.getLib1()}Intent $intent")
    }
}

package com.test.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import com.test.mylibrary1.MyLib1
import com.test.mylibrary4.MainActivity4
import com.test.mylibrary4.MyLib4
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var myLib1: MyLib1

    @Inject
    lateinit var myLib4: MyLib4

    private var jankStat: JankStats? = null

    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        textView = findViewById<TextView>(R.id.text_view)

        lifecycleScope.launchWhenStarted {
            while (true) {
                delay(500)
                val data = logMemory("Loop")
                val total = data.values.sumOf { it.toInt() }.toFloat() / 1024
                textView?.text = "${data.entries.map { "$it \n" }} \n $total"

//                Log.w("Sachin", "Memory Used $total")
            }
        }
//        lifecycleScope.launch {
//            Log.w("Sachin","Task Started")
//            for (i in 0..20) {
//                changeBg()
//                val data = instance.getMoshiResponse()
//                val newData = instance.getJokeCategories()
//            }
//        }.invokeOnCompletion {
//            Log.w("Sachin","Task Ended")
//        }
        textView?.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainActivity4::class.java))
        }
    }

    private fun calculateFib(value: Int): Int {
        return if (value <= 1) value
        else calculateFib(value - 1) + calculateFib(value - 2)
    }

    private fun logMemory(tag: String) = Debug.MemoryInfo().run {
        Debug.getMemoryInfo(this)
//        Log.w("Sachin Memory", "Debug $tag $memoryStats")

        memoryStats.toMutableMap().apply {
            remove("summary.total-pss")
            remove("summary.total-swap")
        }
    }

    private fun changeBg() {
        ContextCompat.getDrawable(this@MainActivity, R.drawable.feed_bg_combat_attempt)
            ?.let { textView?.background = it }
    }

    private fun measureJank() = textView?.run {
        val jankListener = JankStats.OnFrameListener { frameData ->
            // A real app could do something more interesting, like writing the info to local storage and later on report it.
            Log.w("Sachin", frameData.toString())
        }
        val metricsStateHolder = PerformanceMetricsState.getHolderForHierarchy(this)
        metricsStateHolder.state?.putState("Activity", javaClass.simpleName)
        jankStat = JankStats.createAndTrack(window, jankListener)
        jankStat?.isTrackingEnabled = true
    }
}

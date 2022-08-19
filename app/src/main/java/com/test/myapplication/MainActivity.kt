package com.test.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import com.google.firebase.perf.FirebasePerformance
import com.test.mylibrary1.MyLib1
import com.test.mylibrary4.MainActivity4
import com.test.mylibrary4.MyLib4
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var myLib1: MyLib1

    @Inject
    lateinit var myLib4: MyLib4

    private var jankStat: JankStats? = null

    private val jankListener = JankStats.OnFrameListener { frameData ->
        // A real app could do something more interesting, like writing the info to local storage and later on report it.
        Log.w("Sachin", frameData.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text_view)
        val metricsStateHolder = PerformanceMetricsState.getHolderForHierarchy(textView)
        metricsStateHolder.state?.putState("Activity", javaClass.simpleName)
        jankStat = JankStats.createAndTrack(window, jankListener)
        jankStat?.isTrackingEnabled = true

        lifecycleScope.launchWhenCreated {
            for (i in 0..20) {
                val trace =
                    FirebasePerformance.startTrace("Render ${System.currentTimeMillis()} time $i")
                val data = instance.getNetworkResponse().body()
                Log.w("Sachin", "Response : $data")
                textView.text = "$data"
                trace.stop()

                val newData = Json.decodeFromString(
                    NetworkResponse.serializer(),
                    Json.encodeToString(NetworkResponse.serializer(), data!!)
                )
                Log.w("Sachin", "New data = ${newData.updatedAt}")
            }
        }
        Log.w("Sachin", "${myLib1.get()}, ${myLib4.get()}, ${myLib4.getLib1()}")

        textView.setOnClickListener {
            startActivity(Intent(this, MainActivity4::class.java))
        }
    }
}

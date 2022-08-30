package com.test.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var jankStat: JankStats? = null

    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<TextView>(R.id.text_view)

        setUpTask()
        setUpClickListener()
    }

    private fun setUpTask() {
        lifecycleScope.launch {
            textView?.text = "Started"
            for (i in 0..20) {
                textView?.text = "Run $i"
                changeBg()
                Networking.service.getPokemonList()
                Networking.service.getJokeCategories()
                Networking.service.getPokemon()
                Networking.service.getMoshiResponse()
            }
            textView?.text = "Completed"
        }
    }

    private fun setUpClickListener() {
        textView?.setOnClickListener {
            Log.w("Sachin Firebase", "Entries : ${Firebase.remoteConfig.all}")
//            startActivity(Intent(this@MainActivity, MainActivity4::class.java))
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

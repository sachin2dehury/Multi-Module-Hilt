package com.test.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.test.myapplication.databinding.ActivityMainBinding
import com.test.mylibrary4.MainActivity4
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var jankStat: JankStats? = null

    private var binding: ActivityMainBinding? = null

    private val controller = MyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpTask()
        setUpClickListener()

//        setUpEpoxy()
    }

    private fun setUpEpoxy() = binding?.run {
        textView.isVisible = false
        recyclerView.setControllerAndBuildModels(controller)
        lifecycleScope.launch {
            while (true) {
                delay(500)
                controller.requestModelBuild()
            }
        }
    }

    private fun setUpTask() {
        binding?.recyclerView?.isVisible = false
        lifecycleScope.launch {
            binding?.textView?.text = "Started"
            for (i in 0..20) {
                binding?.textView?.text = "Run $i"
                changeBg()
                makeNetworkCalls(i)
            }
            binding?.textView?.text = "Completed"
        }
    }

    private suspend fun makeNetworkCalls(i: Int) {
//        Networking.service.getPokemonList()
        Networking.service.getPokemon()
        Networking.service.getPokemonByIndex(i + 50)
        Networking.service.getPokemonAbility()
        Networking.service.getPokemonSpecies()
        Networking.service.getJokeCategories()
        Networking.service.getMoshiResponse()
    }

    private fun setUpClickListener() {
        binding?.textView?.setOnClickListener {
            Log.w("Sachin Firebase", "Entries : ${Firebase.remoteConfig.all}")
//            startActivity(Intent(this@MainActivity, MainActivity4::class.java))
        }
    }

    private fun changeBg() {
        ContextCompat.getDrawable(this@MainActivity, R.drawable.feed_bg_combat_attempt)
            ?.let { binding?.textView?.background = it }
    }

    private fun measureJank() = binding?.textView?.run {
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

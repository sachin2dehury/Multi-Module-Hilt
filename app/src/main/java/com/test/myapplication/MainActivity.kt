package com.test.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.test.myapplication.EasyTrace.stopTraceWithAdditionalParams
import com.test.myapplication.databinding.ActivityMainBinding
import com.test.mylibrary1.MyLib1
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var jankStat: JankStats? = null

    private var binding: ActivityMainBinding? = null

    private val controller = MyController()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var lib1: dagger.Lazy<MyLib1>

    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        lifecycleScope.launchWhenStarted {
            makeNetworkCalls()
            viewModel.sharedFlow.collectLatest {
                Log.e("Flow_test", "sharedFlow $it")
                if (it == 9) {
                    Toast.makeText(this@MainActivity, "Shared Flow", Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collectLatest {
                Log.e("Flow_test", "stateFlow $it")
                if (it == 9) {
                    Toast.makeText(this@MainActivity, "State Flow", Toast.LENGTH_SHORT).show()
                }
            }
        }
//        setUpTask()
//        setUpClickListener()

//        setUpEpoxy()

//        doOnSuspended()
//        doOnSuspendedWithContext()
//        traceTime()
//        Log.e("Sachin","____")
//        traceTime()

//        val myFlow: Flow<Int> = flow {
//            for (i in 0..100) {
//                emit(i)
//                delay(1000)
//            }
//        }
//        val job = Job()
//        myFlow.onEach {
//            when (it) {
//                5 -> {
//                    Log.e("Flow Sachin", "${System.currentTimeMillis()}")
//                    lifecycleScope.launch {
//                        Log.e("Flow Sachin Inside Started", "${System.currentTimeMillis()}")
//                        delay(10000)
//                        Log.e("Flow Sachin Inside Ended", "${System.currentTimeMillis()}")
//                    }
//                    job.cancel()
//                }
//                else -> {
//                    Log.e("Flow Sachin", "$it")
//                }
//            }
//        }.launchIn(lifecycleScope + job)
    }

    private fun traceTime() {
        Log.e("Sachin Start Time", "Time - ${System.currentTimeMillis()}")
        val trace = EasyTrace.startTraceWithAdditionalParams("New Trace")
        Log.e("Sachin Started Time", "Time - ${System.currentTimeMillis()}")
        trace.stopTraceWithAdditionalParams()
        Log.e("Sachin Ended Time", "Time - ${System.currentTimeMillis()}")
    }

    private fun doOnSuspended() = lifecycleScope.launch(Dispatchers.IO) {
        Log.e("sachin", "doOnSuspended start ${System.currentTimeMillis()}")
        awaitAll(
            async { delay(5000) },
            async { delay(5000) }
        )
        Log.e("sachin", "doOnSuspended end ${System.currentTimeMillis()}")
    }

    private fun doOnSuspendedWithContext() = lifecycleScope.launch {
        Log.e("sachin", "doOnSuspendedWithContext start ${System.currentTimeMillis()}")
        withContext(Dispatchers.IO) {
            awaitAll(
                async { delay(5000) },
                async { delay(5000) }
            )
        }
        Log.e("sachin", "doOnSuspendedWithContext end ${System.currentTimeMillis()}")
    }

    private fun setUpEpoxy() = binding?.run {
        textView.isVisible = false
        controller.setFilterDuplicates(true)
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

    private suspend fun makeNetworkCalls(i: Int=0) {
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

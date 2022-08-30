package com.test.myapplication

import android.os.Build
import android.os.Debug
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.HttpMetric
import com.google.firebase.perf.metrics.Trace
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

object EasyTrace {

    private const val INITIAL_MEMORY_USAGE = "Initial Memory Usage"
    private const val FINAL_MEMORY_USAGE = "Final Memory Usage"

    init {
        Firebase.performance.run {
            isPerformanceCollectionEnabled = true
        }
        Firebase.remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            }
        )
        Firebase.remoteConfig.fetchAndActivate().addOnSuccessListener {
            Log.w("Sachin", "Firebase Remote Config Updated ${Firebase.remoteConfig.all}")
        }
    }

    fun startApiTrace(endPoint: String, requestType: String) =
        Firebase.performance.newHttpMetric(endPoint, requestType).apply { start() }

    fun HttpMetric.stopApiTrace(responseCode: Int, responseSize: Long) {
        setHttpResponseCode(responseCode)
        setResponsePayloadSize(responseSize)
        stop()
    }

    suspend fun startTraceWithAdditionalParams(traceName: String) =
        withContext(Dispatchers.IO) {
            startNewTrace(traceName).apply {
                insertMemoryUsageWithTagIfRequired(INITIAL_MEMORY_USAGE)
            }
        }

    suspend fun Trace.stopTraceWithAdditionalParams() = withContext(Dispatchers.IO) {
        async {
            insertMemoryUsageWithTagIfRequired(FINAL_MEMORY_USAGE)
            stop()
        }
    }

    private fun getMemoryUsageOfApp() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        (getMemoryInfo().values.sumOf { it.toInt() }.toFloat() / 1024).toLong()
    } else null

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getMemoryInfo() = Debug.MemoryInfo().run {
        Debug.getMemoryInfo(this)
        memoryStats.toMutableMap().apply {
            remove("summary.total-pss")
            remove("summary.total-swap")
        }
    }

    private fun Trace.insertMemoryUsageWithTagIfRequired(tag: String, required: Boolean = true) {
        if (required) {
            getMemoryUsageOfApp()?.let { putMetric(tag, it) }
        }
    }

    private fun createNewTrace(traceName: String) =
        Firebase.performance.newTrace(traceName)

    private fun startNewTrace(traceName: String) = createNewTrace(traceName).apply {
        start()
    }
}

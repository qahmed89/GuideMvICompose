package com.example.myapplication.utils.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.myapplication.presentation.MainActivity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SDKWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    companion object {
        const val WORK_NAME = "MyWorker"
    }
    override suspend fun doWork(): Result {
        var deferred: Deferred<Result> = CompletableDeferred(Result.failure())
        val data = try {
            withContext(Dispatchers.IO) {
                // Call the suspend function
                if (SDK.getInstance()) {
                    Log.i("Worker" , "init")
                    deferred = async { Result.success() }
                    return@withContext
                }
                SDK.init(callBack = object : ListnerSDK {
                    override fun onSuccess() {
                        Log.i("Worker" , "init")
                        deferred = async { Result.success() }
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK)
                        applicationContext.startActivity(intent)
                    }

                    override fun OnError() {
                        deferred = async { Result.failure() }
                    }

                })
            }
        } catch (e: Exception) {
            // Handle exceptions
            return Result.failure()
        }

        return deferred.await()
        // Indicate whether the work finished successfully with the Result

    }
}


class SDK() {
    companion object {
        private var instance: Boolean? = null

        @Throws(java.lang.NullPointerException::class)
        fun getInstance(): Boolean = instance ?: false
        suspend fun init(callBack: ListnerSDK) {
            delay(1000)
            instance = true
            callBack.onSuccess()
        }
    }


}

interface ListnerSDK {
    fun onSuccess()
    fun OnError()
}
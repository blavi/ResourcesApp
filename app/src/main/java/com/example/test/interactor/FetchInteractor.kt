package com.example.test.interactor

import android.content.Context
import android.net.ConnectivityManager
import com.example.test.mvi.change.Change
import kotlinx.coroutines.flow.Flow

abstract class FetchInteractor (val context: Context) {
     abstract suspend fun fetch(): Flow<Change>
     abstract suspend fun fetchFromDB(): Change

     fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var res = false
        connectivityManager.let {
            it.activeNetworkInfo?.let { networkInfo ->
                res = networkInfo.isAvailable
            }
        }
        return res
    }
}